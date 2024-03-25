package learnQA;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

import java.util.HashMap;
import java.util.Map;

public class HelloWorldTest {
    @Test
    public void testRestAssured() {
        /*Map<String, String> params = new HashMap<>();
        params.put("name", "John");*/

        /*JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                //.get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();*/

        /*Map<String, String> ans = new LinkedHashMap<>();
        ans = response.get();
        System.out.println(ans);*/
        /*Map<String, Object> body = new HashMap<>();
        body.put("param1", "value1");
        body.put("param2", "value2");*/
        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");

        Response responseForGet = RestAssured
                .given()
                //.headers(headers)
                .body(data)
                //.redirects()
                //.follow(false)
                .when()
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                //.get("https://playground.learnqa.ru/api/get_json_homework")
                .andReturn();

        String authCookie = responseForGet.getCookie("auth_cookie");

        Map<String, String> cookies = new HashMap<>();
        cookies.put("auth_cookie", authCookie);

        Response responseForCheck = RestAssured
                .given()
                .body(data)
                .cookies(cookies)
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();

        responseForCheck.print();


        /*int statusCode = response.getStatusCode();
        System.out.println(statusCode);*/
        /*System.out.println("\nPretty text:");
        response.prettyPrint();

        System.out.println("\nHeaders:");
        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

        System.out.println("\nCookies:");
        Map<String, String> responseCookies = response.getCookies();
        System.out.println(responseCookies);

        String authCookie = response.getCookie("auth_cookie");
        System.out.println(authCookie);*/
        //String locationHeaders = response.getHeader("Location");
        //System.out.println(locationHeaders);
    }
}
