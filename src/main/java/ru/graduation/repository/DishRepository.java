package ru.graduation.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Dish;

import java.util.List;

import static ru.graduation.util.validation.ValidationUtil.checkBelong;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.id =:dishId AND d.restaurant.id =:restId")
    Dish get(int restId, int dishId);

    default Dish getBelong(int restId, int dishId) {
        return checkBelong(get(restId, dishId), restId, dishId);
    }

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id =:restIdORDER ORDER BY d.price")
    List<Dish> getAll(int restId);
}