package ru.graduation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Dish;
import ru.graduation.repository.DishRepository;
import ru.graduation.repository.RestaurantRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public List<Dish> getAll(int restId) {
        restaurantRepository.getExisted(restId);
        return dishRepository.getAll(restId);
    }

    public Dish get(int restId, int dishId) {
        return dishRepository.getBelong(restId, dishId);
    }

    @Transactional
    public Dish create(int restId, Dish dish) {
        dish.setRestaurant(restaurantRepository.getExisted(restId));
        return dishRepository.save(dish);
    }

    @Transactional
    public void update(int restId, Dish dish, int dishId) {
        dishRepository.getBelong(restId, dishId);
        dish.setRestaurant(restaurantRepository.getExisted(restId));
        dishRepository.save(dish);
    }

    public void delete(int dishId) {
        dishRepository.deleteExisted(dishId);
    }
}
