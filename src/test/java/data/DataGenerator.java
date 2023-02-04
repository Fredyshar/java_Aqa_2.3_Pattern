package data;

import com.github.javafaker.Faker;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    private static Faker faker;


    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        var city = Arrays.asList("Москва", "Смоленск", "Тамбов", "Саратов", "Саранск", "Барнаул", "Оренбург", "Владимир", "Иваново", "Севастополь", "Мурманск", "Ростов-на-Дону");
        Random random = new Random();
        return city.get(random.nextInt(city.size()));
    }

    public static String generateName(String locale) {
        faker = new Faker(new Locale(locale));
        return faker.name().name();
    }

    public static String generatePhone(String locale) {
        faker = new Faker(new Locale(locale));
        return faker.phoneNumber().cellPhone();
    }

    public static class Registration {

        private Registration() {
            String city;
            String name;
            String phone;
        }


        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(), generateName(locale), generatePhone(locale));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }

}
