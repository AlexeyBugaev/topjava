package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Modifying
    @Transactional
    @Query(name = Meal.DELETE)
    int deleteMealByIdAndUserId(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Meal save(Meal meal);

    @Transactional
    @Modifying
    @Query("UPDATE Meal meal SET meal.description=:description, meal.calories=:calories, meal.dateTime=:date_time  WHERE meal.id=:id AND meal.user.id=:userId")
    int update(@Param("id") int id, @Param("description") String description, @Param("calories") int calories, @Param("date_time") LocalDateTime dateTime, @Param("userId") int userId);

    @Override
    Optional<Meal> findById(Integer id);

    @Modifying
    @Query(name = Meal.ALL_SORTED)
    List<Meal> getAllByUserId(@Param("userId") int userId);

    @Modifying
    @Query(name = Meal.GET_BETWEEN)
    List<Meal> getBetween(@Param("userId") int userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
