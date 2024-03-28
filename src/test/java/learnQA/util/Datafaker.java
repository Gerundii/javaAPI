package learnQA.util;

import net.datafaker.Faker;

import java.util.Locale;


public class Datafaker {
    public static String generateEmail(){
        Faker faker = new Faker();
        //Faker faker = new Faker(new Locale("ru"));
        return faker.internet().emailAddress();
    }
}
