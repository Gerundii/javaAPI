package learnQA.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import learnQA.lib.Assertions;
import learnQA.lib.BaseTestCase;
import learnQA.util.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {
    String cookie;
    String header;
    int userId;
    String[] unexpectedFields = {"firstName", "lastName",  "email"};
    String[] expectedFields = {"username", "firstName", "lastName",  "email"};
    @Test
    @Description("This test checks the displayed information about the user without authorization")
    @DisplayName("Test to verify that only the user's name is displayed without authorization")
    public void testGetUserDataWithoutAuth() {
        Response responseUserData = apiCoreRequests
                .makeGetRequestWithoutTokenAndCooke("https://playground.learnqa.ru/api/user/2");

        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotFields(responseUserData, unexpectedFields);
    }
    @Test
    @Description ("This test checks the display of information about the authorized user")
    @DisplayName("Test to verify that all info is displayed about the authorized user")
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

        Assertions.assertJsonHasFields(responseUserData, expectedFields);
    }
    @Test
    @Description ("The test checks the display of information about a user other than an authorized one")
    @DisplayName("Test to verify that only the user's name is displayed about a user other than an authorized one")
    public void testGetUserDetailsAuthAsAnotherUser() {
        Map<String,String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        this.userId = getIntFromJson(responseCreateAuth, "id");

        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        this.cookie = getCookie(responseGetAuth, "auth_sid");
        this.header = getHeader(responseGetAuth, "x-csrf-token");

        Response responseUserData = apiCoreRequests
                .makeGetRequestWithCookieAndToken("https://playground.learnqa.ru/api/user/" + userId, this.header ,this.cookie);

        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotFields(responseUserData, unexpectedFields);
    }
}
