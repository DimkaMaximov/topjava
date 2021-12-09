package ru.javawebinar.topjava.web;

import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static final AtomicInteger userId = new AtomicInteger(0);

    public static int authUserId() {
        return userId.get();
    }

    public static void setUserId(int selectedUserId){
        userId.set(selectedUserId);
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}