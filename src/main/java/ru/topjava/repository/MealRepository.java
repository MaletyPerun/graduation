package ru.topjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.error.DataConflictException;
import ru.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

import static ru.topjava.util.validation.ValidationUtil.checkBelong;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restId ORDER BY m.price DESC")
    List<Meal> getAll(int restId);


    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restId AND m.id=:mealId")
    Optional<Meal> get(int restId, int mealId);

    default Optional <Meal> getBelong(int restId, int mealId) {
        return checkBelong(get(restId, mealId), restId, mealId);
    }

    // TODO: 29/08/2022 проверить пригодность еды
}