package com.lionbridge.aurora;

import com.lionbridge.aurora.step.HttpsSteps;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.command.DeployResourceCommandStep1;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;

import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.time.Duration;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@Slf4j
public abstract class BaseTest {

    protected static ZeebeClient client;
    protected static HttpsSteps steps;
    private static final String GATEWAY_ADDRESS = "";
    private static final String AUDIENCE = "";
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String O_AUTH_API = "https://login.cloud.camunda.io/oauth/token";
    private static final String PROCESS_INSTANCE_KEY = "Process Instance Key ";
    protected static final String BPMN_PROCESS_ID = "Process_06o3eguiiyu";
    public static String FMS_TOKEN;

    private static String url = "";
    private static String body = "";


    @BeforeAll
    public static void setUp() {
        OAuthCredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .audience(AUDIENCE)
                        .clientId(CLIENT_ID)
                        .clientSecret(CLIENT_SECRET)
                        .authorizationServerUrl(O_AUTH_API)
                        .build();

        client = ZeebeClient.newClientBuilder()
                .gatewayAddress(GATEWAY_ADDRESS)
                .credentialsProvider(credentialsProvider)
                .build();

        steps = new HttpsSteps();
        FMS_TOKEN = steps.getToken(url, body);
    }

    @AfterAll
    public static void close(){
        client.close();
    }

    protected DeploymentEvent initDeployment(String path) {
        return client.newDeployResourceCommand()
                .addResourceFromClasspath(path)
                .send()
                .join();
    }

    protected DeploymentEvent deployResources(final String... resources) {
        final DeployResourceCommandStep1 commandStep1 = client.newDeployResourceCommand();

        DeployResourceCommandStep1.DeployResourceCommandStep2 commandStep2 = null;
        for (final String process : resources) {
            if (commandStep2 == null) {
                commandStep2 = commandStep1.addResourceFromClasspath(process);
            } else {
                commandStep2 = commandStep2.addResourceFromClasspath(process);
            }
        }

        return commandStep2.send().join();
    }

    protected ProcessInstanceEvent initProcessInstance(String bpmnProcessId, String path) {

        ProcessInstanceEvent event = client.newCreateInstanceCommand()
                .bpmnProcessId(bpmnProcessId)
                .latestVersion()
                .variables(path)
                .requestTimeout(Duration.ofSeconds(30))
                .send()
                .join();

        log.info("Started instance for processDefinitionKey='{}', bpmnProcessId='{}', version='{}' with processInstanceKey='{}'",
                event.getProcessDefinitionKey(), event.getBpmnProcessId(), event.getVersion(), event.getProcessInstanceKey());
        return event;
    }


    protected ProcessInstanceResult initProcessInstanceResult(String bpmnProcessId, String variablePath, String output) {
        ProcessInstanceResult event =  client
                .newCreateInstanceCommand()
                .bpmnProcessId(bpmnProcessId)
                .latestVersion()
                .variables(variablePath)
                .withResult()
                .requestTimeout(Duration.ofSeconds(45))// to await the completion of process execution and return result
                .send()
                .join();

        log.info("Started instance for processDefinitionKey='{}', bpmnProcessId='{}', version='{}' with processInstanceKey='{}'",
                event.getProcessDefinitionKey(), event.getBpmnProcessId(), event.getVersion(), event.getProcessInstanceKey());
        log.info(event.getVariable(output).toString());

        return event;
    }


    protected void completeServiceTask(final String jobType) {
        completeServiceTasks(jobType, 1);
    }

    protected void completeServiceTasks(final String jobType, final int count) {

        final var activateJobsResponse =
                client.newActivateJobsCommand().jobType(jobType).maxJobsToActivate(count).send().join();

        final int activatedJobCount = activateJobsResponse.getJobs().size();
        if (activatedJobCount < count) {
            Assertions.fail(
                    "Unable to activate %d jobs, because only %d were activated."
                            .formatted(count, activatedJobCount));
        }

        for (int i = 0; i < count; i++) {
            final var job = activateJobsResponse.getJobs().get(i);
            System.out.println("JOB KEY: " +job.getKey());

            client.newCompleteCommand(job.getKey()).send().join();
        }
    }

    protected static void openJobWorker(String jobType) {
      var  activateJobsResponse =  client.newWorker().jobType(jobType).handler(new ExampleJobHandler()).open();
        System.out.println(activateJobsResponse);
    }



    private static class ExampleJobHandler implements JobHandler {
        @Override
        public void handle(final JobClient client, final ActivatedJob job) {
            // here: business logic that is executed with every job
            System.out.println(job.getVariables().toString());
            client.newCompleteCommand(job.getKey()).send().join();
        }
    }

}
