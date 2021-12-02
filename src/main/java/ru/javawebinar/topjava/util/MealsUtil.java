package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MealsUtil {

    public static final List<Meal> MEAL_LIST = new ArrayList<>();

    static {
        MEAL_LIST.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        MEAL_LIST.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        MEAL_LIST.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        MEAL_LIST.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 1, 0), "Еда на граничное значение", 100));
        MEAL_LIST.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        MEAL_LIST.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        MEAL_LIST.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

//    public static final List<Meal> MEAL_LIST = Arrays.asList(
//            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
//            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
//            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
//            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 1, 0), "Еда на граничное значение", 100),
//            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
//            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
//            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
//    );

//
//public static final Map<Meal, Integer> MEAL_LIST = new ConcurrentHashMap<>();
//    {
//        MEAL_LIST.put (new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500), 1);
//        MEAL_LIST.put (new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000), 2);
//        MEAL_LIST.put (new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500), 3);
//        MEAL_LIST.put (new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 1, 0), "Еда на граничное значение", 100), 4);
//        MEAL_LIST.put (new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000), 5);
//        MEAL_LIST.put (new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500), 6);
//        MEAL_LIST.put (new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410), 7);
//    }

    public static void main(String[] args) {

        final int CALORIES_PER_DAY = 2000;

        List<MealTo> mealsTo = filteredByStreams(MEAL_LIST, LocalTime.of(7, 0), LocalTime.of(12, 0), CALORIES_PER_DAY);
        mealsTo.forEach(System.out::println);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}