package learnQA.util;

import net.datafaker.Faker;

import java.util.Locale;


public class Datafaker {
    public static String generateEmail(){
        Faker faker = new Faker();
        //Faker faker = new Faker(new Locale("ru"));
        return faker.internet().emailAddress();
    }
    public static String generateFirstName(){
        Faker faker = new Faker();
        return faker.name().firstName();
    }
    public static String generateLastName(){
        Faker faker = new Faker();
        return faker.name().lastName();
    }
    public static String generateUserName(){
        Faker faker = new Faker();
        return faker.internet().username();
    }
}
