package com.lionbridge.aurora.step.tests.fms;

import com.lionbridge.aurora.BaseTest;
import com.lionbridge.aurora.step.Response;
import org.junit.jupiter.api.Test;

import static com.lionbridge.aurora.utils.TestUtils.readResource;

public class FileManagementSystemApiTest extends BaseTest {

    String fmsPath = "";
    private static final String FMS_CREATE_CONTAINER_BODY_JSON = "/fms/requests/fmsCreateContainerRequestBody.json";

    @Test
    public void testHappyPathCreateContainer() {
        Response response = steps
                .withRestEndpoint(fmsPath, "POST")
                .withHeader("Authorization", FMS_TOKEN)
                .withJsonBody(readResource(FMS_CREATE_CONTAINER_BODY_JSON))
                .expectStatus(200)
                .execute();

        String containerId = response.getJsonStringField("id");
        System.out.println(response.getResponseBody());
    }

    @Test
    public void test() {

        Response response = steps
                .withRestEndpoint(fmsPath, "POST")
                .withHeader("Authorization", "BeGEtNDE1zIiwic2NvcGUiOlsiZm1zLmFwaS5kZWZhdWx0Il19.VqC-VzQmnEhjx3f-FJZNDPoBtn7zWZkfbuU3EfWKtLY7U_qUmjRf6Xl710QDwaQdTtbkmN3onOQW5rDDmBWVWlk0Kpwqjvaw5jtzL4ZIU1lzEi1wUKHGbm4SqWuwvnJJplkUPFCvbAASpQlVLr_0kZU1ZeTlRowsRWJgb_46S4Yqg4LWG99hVHQXT7F2XbsyosmVgahuWCSJ497_ewWckjtweyT-hzJKDtwJXmCw6NqkIMiA3iMkcerxZgIItT4S_AjSQ0LE8qowyz6J4CkPSFoV__prAWp84Osk7gOTJ_qP6r4EdX0lWMC824sp0LzqXerQ559_1c2aFANhquLFZg")
                .withJsonBody(readResource(FMS_CREATE_CONTAINER_BODY_JSON))
                .expectStatus(200)
                .execute();
        System.out.println(response.getResponseBody());
    }
}

