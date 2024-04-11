package learnQA.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import learnQA.lib.Assertions;
import learnQA.lib.BaseTestCase;
import learnQA.util.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

@Epic("Registration cases")
@Feature("Registration")
public class UserRegisterTest extends BaseTestCase {
    @Test
    @Description("This test successfully create user")
    @DisplayName("Test positive create user")
    public void testCreateUserSuccessfully() {
        Map<String,String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }
    @Test
    @Description ("This test unsuccessfully create user with existing email")
    @DisplayName("Test negative create user with existing email")
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@examle.com";
        Map<String,String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
    }
    @Test
    @Description ("This test unsuccessfully create user with invalid email")
    @DisplayName("Test negative create user with invalid email")
    public void testCreateUserWithInvalidEmail() {
        String email = "vinkotovexamle.com";
        Map<String,String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Invalid email format");
    }
    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    @Description ("This test unsuccessfully create user with missing required param")
    @DisplayName("Test negative create user with missing required param")
    public void testCreateUserWithMissingRequiredParam(String param) {
        Map<String,String> userData = DataGenerator.getRegistrationData();
        userData.remove(param);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The following required params are missed: " + param);
    }
    @Test
    @Description ("This test unsuccessfully create user with short name")
    @DisplayName("Test negative create user with short name")
    public void testCreateUserWithShortName() {
        String firstName = "A";
        Map<String,String> userData = new HashMap<>();
        userData.put("firstName", firstName);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        System.out.println(responseCreateAuth.prettyPrint());

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too short");
    }
    @Test
    @Description ("This test unsuccessfully create user with long name")
    @DisplayName("Test negative create user with long name")
    public void testCreateUserWithLongName() {
        String firstName = "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdea";
        Map<String,String> userData = new HashMap<>();
        userData.put("firstName", firstName);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        System.out.println(responseCreateAuth.prettyPrint());

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too long");
    }
}
