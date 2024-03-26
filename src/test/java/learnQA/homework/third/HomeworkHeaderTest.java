package learnQA.homework.third;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeworkHeaderTest {
    @Test
    public void testHomeworkHeader() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Headers headers = response.getHeaders();
        assertTrue(headers.hasHeaderWithName("x-secret-homework-header"), "Response doesn't have header with name 'x-secret-homework-header'");
        assertEquals("Some secret value", headers.getValue("x-secret-homework-header"), "Header value is not 'Some secret value'");
        assertEquals(17, headers.getValue("x-secret-homework-header").length(), "The length is different");
    }
}
