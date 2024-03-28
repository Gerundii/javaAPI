package learnQA.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import learnQA.lib.Assertions;
import learnQA.lib.BaseTestCase;
import org.junit.jupiter.api.Test;

public class UserGetTest extends BaseTestCase {
    @Test
    public void testGetUserDataWithoutAuth() {
        Response responseUserData = RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        Assertions.assertJsonHasKey(responseUserData, "username");
        Assertions.assertJsonHasNotKey(responseUserData, "firstName");
        Assertions.assertJsonHasNotKey(responseUserData, "lastName");
        Assertions.assertJsonHasNotKey(responseUserData, "email");
    }
}
