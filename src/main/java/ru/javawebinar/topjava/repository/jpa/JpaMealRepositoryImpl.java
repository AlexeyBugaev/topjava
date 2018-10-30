package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if(meal.isNew()){
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        }
        else{
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Query query = em.createQuery("DELETE FROM Meal meal WHERE meal.id=:id AND meal.user.id=:user_id");
        return query.setParameter("id", id).setParameter("user_id", userId).executeUpdate() != 0;
    }

    @Override
    @Transactional
    public Meal get(int id, int userId) {
        Query query = em.createQuery("SELECT meal FROM Meal meal WHERE meal.id=:id AND meal.user.id=:user_id");
        query.setParameter("id", id).setParameter("user_id", userId);
        return em.getReference(Meal.class,id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        TypedQuery<Meal> query = em.createQuery("SELECT meal FROM Meal meal WHERE meal.user.id=:user_id", Meal.class);
        query.setParameter("user_id", userId);
        return query.getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        TypedQuery<Meal> query = em.createQuery("SELECT meal FROM Meal meal WHERE meal.user.id=:user_id AND meal.dateTime > :startDate AND meal.dateTime < :endDate", Meal.class);
        query.setParameter("user_id", userId).setParameter("startDate", startDate).setParameter("endDate", endDate);
        return query.getResultList();
    }
}