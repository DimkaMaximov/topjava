package ru.javawebinar.topjava.web;

import javafx.util.converter.LocalDateTimeStringConverter;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        System.out.println("Сервер запущен");
        String action= req.getParameter("action");

        if(action != null){
            log.info("Some data deleted");
            if (action.equalsIgnoreCase("delete")) {
                MealsUtil.MEAL_LIST.remove(Integer.parseInt(req.getParameter("id")));
                System.out.println("Параметр удален");
            } else if(action.equalsIgnoreCase("update")){
                int id = Integer.parseInt(req.getParameter("id"));
                System.out.println("Параметр обновлен");
            }
            resp.sendRedirect("meals");
        }
        else {
            System.out.println("Работа после цикла");
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(MealsUtil.MEAL_LIST, LocalTime.of(1, 0), LocalTime.of(23, 0), 2000);
            req.setAttribute("mealsTo", mealsTo);
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description");
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("datetime"));
        System.out.println(localDateTime.toString());
        int calories = Integer.parseInt(req.getParameter("calories"));
        MealsUtil.MEAL_LIST.add(new Meal(localDateTime, description, calories));
        log.info("Parameter added");
        resp.sendRedirect("meals");

    }
}
