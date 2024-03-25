package learnQA;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GetMessagePartTest {
    @Test
    public void testRestAssured() {
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        String secondMessageFirstWay = response.get("messages[1].message");
        System.out.println(secondMessageFirstWay + "\n");

        List<String> messages = response.get("messages.message");
        String secondMessageSecondWay = messages.get(1);
        System.out.println(secondMessageSecondWay + "\n");

        Map<String, String> secondMessageThirdWay = response.get("messages.find{it.message='And this is a second message'}");
        System.out.println(secondMessageThirdWay.get("message") + "\n");

        Response responseRet = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .andReturn();

        responseRet.prettyPrint();
    }
}

