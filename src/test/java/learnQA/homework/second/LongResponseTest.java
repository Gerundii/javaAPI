package learnQA.homework.second;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class LongResponseTest {
    String url = "https://playground.learnqa.ru/ajax/api/longtime_job";
    String validToken = null;
    String errorToken = "wM1ojNxoDMxxxNy0yMw0CNyAjM";
    int seconds = 0;
    String status = null;
    String error = null;
    Map<String, String> errorParams = new HashMap<>();
    Map<String, String> validParams = new HashMap<>();
    JsonPath response;
    @Test
    public void testLongResponse() throws InterruptedException {
        errorParams.put("token", errorToken);

        JsonPath response = requestGetWithoutParams(url);

        validToken = response.get("token");
        seconds = response.getInt("seconds");
        validParams.put("token", validToken);

        response = requestGetWithParams(url, validParams);
        getStatus(response);

        Thread.sleep(seconds * 1000);

        response = requestGetWithParams(url, validParams);
        getStatus(response);

        response = requestGetWithParams(url, errorParams);
        getError(response);
    }

    public JsonPath requestGetWithoutParams(String url) {
        JsonPath response = RestAssured
                .when()
                .get(url)
                .jsonPath();
        return response;
    }

    public JsonPath requestGetWithParams(String url, Map<String, String> params) {
        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .when()
                .get(url)
                .jsonPath();
        return response;
    }
    public void getStatus (JsonPath response) {
        status = response.get("status");
        System.out.println("Status is : " + status);
    }
    public void getError (JsonPath response) {
        error = response.get("error");
        System.out.println("Error is : " + error);
    }
}
