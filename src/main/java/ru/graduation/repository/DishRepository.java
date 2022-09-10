package ru.graduation.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Dish;

import java.util.List;

import static ru.graduation.util.validation.ValidationUtil.checkBelong;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT m FROM Dish m WHERE m.id =:dishId AND m.restaurant.id =:restId")
    Dish get(int restId, int dishId);

    default Dish getBelong(int restId, int dishId) {
        return checkBelong(get(restId, dishId), restId, dishId);
    }
}