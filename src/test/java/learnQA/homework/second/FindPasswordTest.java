package learnQA.homework.second;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindPasswordTest {
    String file = "passwords.txt";
    List<String> passwords = null;
    String login = "super_admin";
    String secretPasswordUrl = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
    String checkAuthCookieUrl = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";
    String authCookie = null;
    Map<String, String> data = new HashMap<>();
    Map<String, String> cookies = new HashMap<>();
    String result = null;
    String goodAuth = "You are authorized";
    @Test
    public void testFindPassword () {
        passwords = readPasswordsFromFile(file);
        data.put("login", login);
        for (String password : passwords) {
            data.put("password", password);
            authCookie = requestPostSecretPassword(data, secretPasswordUrl);
            cookies.put("auth_cookie", authCookie);
            result = requestGetCheckCookie(cookies, checkAuthCookieUrl);
            if (result.equals(goodAuth)) {
                System.out.println(password);
                return;
            }
        }
    }
    public List<String> readPasswordsFromFile (String file) {
        Path path = Path.of(file);
        List<String> passwords;
        try {
            passwords = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return passwords;
    }

    public String requestPostSecretPassword (Map<String, String> data, String url) {

        Response response = RestAssured
                .given()
                .body(data)
                .when()
                .post(url)
                .andReturn();

        return response.getCookie("auth_cookie");
    }

    public String requestGetCheckCookie (Map<String, String> cookies, String url) {

        Response response = RestAssured
                .given()
                .cookies(cookies)
                .when()
                .post(url)
                .andReturn();

        return response.htmlPath().getString("html.body");
    }
}
