package learnQA.homework.second;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class RedirectTest {
    @Test
    public void testRedirect() {
        String url = "https://playground.learnqa.ru/api/long_redirect";
        int statusCode = 0;
        int count = 0;
        do {
            count++;
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(url)
                    .andReturn();

            url = response.getHeader("Location");
            statusCode = response.getStatusCode();

            System.out.println(statusCode + " " + url + " " + count);
        } while (statusCode != 200);
    }
}
