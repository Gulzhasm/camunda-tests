package com.lionbridge.aurora.step.tests.twWorker;

import com.lionbridge.aurora.BaseTest;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



import static com.lionbridge.aurora.utils.TestUtils.readResource;

@Slf4j
public class TWConvertWorkerTest extends BaseTest {

    private static final String TW_CONVERT_JSON = "/tw_worker/twConvertWorker.json";
    private static final String CONVERTED_FILE = "convertedFile";

    @Test
    void testTWConvertHappyPath()  {
        ProcessInstanceEvent processInstance = initProcessInstance(BPMN_PROCESS_ID, readResource(TW_CONVERT_JSON));
        Assertions.assertNotNull(processInstance);
        openJobWorker("tw-analysis-worker");
    }

    @Test
    void testTWConvertResultHappyPath() {
        ProcessInstanceResult result = initProcessInstanceResult(BPMN_PROCESS_ID, readResource(TW_CONVERT_JSON), CONVERTED_FILE);
        Assertions.assertTrue(result
                .getVariable(CONVERTED_FILE).toString()
                .contains("buzzy word"));
   }
}
