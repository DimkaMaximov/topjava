package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, USER_ID));
        save(new Meal(LocalDateTime.of(2021, Month.DECEMBER, 9, 10, 0), "Завтрак Админа", 500), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2020, Month.DECEMBER, 9, 13, 0), "Обед Админа", 1000), ADMIN_ID);
    }


    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) meal.setId(counter.incrementAndGet());
        if (!repository.containsKey(userId)) repository.put(userId, new ConcurrentHashMap<>());
        if (repository.get(userId).containsKey(meal.getId())){
            //repository.get(userId).put(meal.getId(), meal);
        }
        //return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        repository.get(userId).put(meal.getId(), meal);
        return meal;
    }


    @Override
    public boolean delete(int id, int userId) {
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }

    @Override
    public List<Meal> isBetweenHalfOpen(LocalDate start, LocalDate end, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), start, end.plusDays(1)))
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}

