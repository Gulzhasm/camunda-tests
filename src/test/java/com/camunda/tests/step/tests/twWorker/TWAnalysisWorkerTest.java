package com.camunda.tests.step.tests.twWorker;

import com.camunda.tests.BaseTest;
import com.camunda.tests.utils.TestUtils;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class TWAnalysisWorkerTest extends BaseTest {

        private static final String TW_ANALYSIS_WORKER_JSON = "/tw_worker/twAnalysisWorker.json";
        private static final String ANALYSIS_RESULT = "analysisResults";

        @Test
        void testTWAnalysisHappyPath()  {
            ProcessInstanceEvent processInstance = initProcessInstance(BPMN_PROCESS_ID, TestUtils.readResource(TW_ANALYSIS_WORKER_JSON));
            Assertions.assertNotNull(processInstance);
        }

        @Test
        void testTWAnalysisResultHappyPath() {
            ProcessInstanceResult result = initProcessInstanceResult(BPMN_PROCESS_ID, TestUtils.readResource(TW_ANALYSIS_WORKER_JSON), ANALYSIS_RESULT);
            Map<String, Object> map = result.getVariablesAsMap();
            System.out.println(map.get(ANALYSIS_RESULT));

            Assertions.assertTrue(result.getVariable(ANALYSIS_RESULT).toString().contains("fuzzy"));
            Assertions.assertTrue(result.getVariable("analysisFileInfo").toString().contains("some buzzy words"));
        }
}
