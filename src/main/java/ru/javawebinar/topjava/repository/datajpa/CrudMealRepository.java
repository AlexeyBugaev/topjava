package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query(name = Meal.DELETE)
    int delete(@Param("id") int id);

    @Transactional
    Meal save(Meal meal);

    @Transactional
    @Modifying
    @Query("UPDATE Meal meal SET meal.description=:description, meal.calories=:calories, meal.dateTime=:date_time  WHERE meal.id=:id")
    void update();

    @Override
    Optional<Meal> findById(Integer id);

    @Override
    @Modifying
    @Query(name = Meal.ALL_SORTED)
    List<Meal> findAll();

    @Modifying
    @Query(name = Meal.GET_BETWEEN)
    List<Meal> getBetween();
}
