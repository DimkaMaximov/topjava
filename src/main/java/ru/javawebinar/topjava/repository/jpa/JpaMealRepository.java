package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em.getReference(User.class, userId);
        meal.setUser(user);
        if(meal.isNew()){
            em.persist(meal);
        } else {
            em.merge(meal);
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createQuery("delete from Meal m where m.id =:id").setParameter("id", id).executeUpdate() !=0;
    }

    @Override
    public Meal get(int id, int userId) {
        //return em.find(Meal.class, id);
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id =?1 and m.id = ?2", Meal.class)
                .setParameter(1, userId).setParameter(2, id).getSingleResult();
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = em.createQuery("SELECT m FROM Meal m WHERE m.user.id =:userId", Meal.class)
                .setParameter("userId", userId).getResultList();
        return meals;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        List<Meal> meals = em.createQuery("select m from Meal m where m.user.id = ?1 and m.dateTime >= ?2 and m.dateTime <= ?3", Meal.class)
                .setParameter(1, userId).setParameter(2, startDateTime).setParameter(3, endDateTime).getResultList();
        return meals;
    }
}