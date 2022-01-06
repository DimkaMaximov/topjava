package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ;

    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.now(), "Завтрак ТЕСТ", 700);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "Завтрак ТЕСТ", 700);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal);
        updated.setDescription("Новый ТЕСТ завтрак");
        updated.setCalories(1000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }


    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        MealTestData.assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }
}
