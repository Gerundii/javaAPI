package learnQA.homework.third;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAgentTest {
    @ParameterizedTest
    @CsvFileSource (resources = "/useragent.csv", delimiter = '|')
    public void testUserAgent(String userAgent, String platform, String browser, String device) {
        Response response = RestAssured
                .given()
                .header("User-Agent", userAgent)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .andReturn();

        assertAll("Какой параметр не совпал",
                () -> assertEquals(platform, response.jsonPath().get("platform")),
                () -> assertEquals(browser, response.jsonPath().get("browser")),
                () -> assertEquals(device, response.jsonPath().get("device"))
        );
    }
}