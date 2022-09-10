package ru.graduation.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Dish;

import java.util.List;

import static ru.graduation.util.validation.ValidationUtil.checkBelong;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

//    @Query("SELECT m FROM Dish m WHERE m.restaurant.id=:restId ORDER BY m.price DESC")
//    List<Dish> getAll(int restId);

    @Query("SELECT m FROM Dish m WHERE m.id =:mealId AND m.restaurant.id =:restId")
    Dish get(int restId, int mealId);

    default Dish getBelong(int restId, int mealId) {
        return checkBelong(get(restId, mealId), restId, mealId);
    }
}