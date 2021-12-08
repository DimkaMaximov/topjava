package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {

    public static final List<User> users = Arrays.asList(
            new User(1, "Cloud", "cloud@mail.ru", "cloud123", Role.USER),
            new User(2, "Barret", "barret@mail.ru", "barret123", Role.USER),
            new User(3, "Tifa", "tifa@mail.ru", "tifa123", Role.USER),
            new User(4, "Aerith", "aerith@mail.ru", "aerith123", Role.USER),
            new User(5, "Red-13", "red-13@mail.ru", "red13123", Role.USER)
    );

    public static List<User> filterByAlphabet(Collection<User> users){
        return users.stream().sorted((a, b) -> a.getName().compareTo(b.getName())).collect(Collectors.toList());
    };

}
