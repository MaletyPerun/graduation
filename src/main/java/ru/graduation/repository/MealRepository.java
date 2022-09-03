package ru.graduation.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Meal;

import java.util.List;

import static ru.graduation.util.validation.ValidationUtil.checkBelong;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restId ORDER BY m.price DESC")
    List<Meal> getAll(int restId);


    @Query("SELECT m FROM Meal m WHERE m.id =:mealId AND m.restaurant.id =:restId")
    Meal get(int restId, int mealId);

    default Meal getBelong(int restId, int mealId) {
        return checkBelong(get(restId, mealId), restId, mealId);
    }
}