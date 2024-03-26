package learnQA.homework.third;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeworkCookieTest {
    @Test
    public void testHomeworkCookie() {
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        Map<String, String> cookies = response.getCookies();
        assertTrue(cookies.containsKey("HomeWork"), "Response doesn't have cookie with name HomeWork");
        assertEquals("hw_value", cookies.get("HomeWork"), "Cookie value is not 'hw_value'");
        assertEquals(8, cookies.get("HomeWork").length(), "The length is different");
    }
}
