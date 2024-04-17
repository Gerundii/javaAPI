package learnQA.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import learnQA.lib.Assertions;
import learnQA.lib.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {
    String cookie;
    String header;
    int userIdOnAuth;
    @Test
    public void testGetUserDataWithoutAuth() {
        Response responseUserData = apiCoreRequests
                .makeGetRequestWithoutTokenAndCooke("https://playground.learnqa.ru/api/user/2");

        String[] unexpectedFields = {"firstName", "lastName",  "email"};

        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotFields(responseUserData, unexpectedFields);
    }
    @Test
    public void testGetUserDetailsAuthAsSameUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = getCookie(responseGetAuth, "auth_sid");
        this.header = getHeader(responseGetAuth, "x-csrf-token");

        Response responseUserData = apiCoreRequests
                .makeGetRequestWithCookieAndToken("https://playground.learnqa.ru/api/user/2", this.header ,this.cookie);

        String[] expectedFields = {"username", "firstName", "lastName",  "email"};

        Assertions.assertJsonHasFields(responseUserData, expectedFields);
    }
}
