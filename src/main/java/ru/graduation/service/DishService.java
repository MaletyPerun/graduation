package ru.graduation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Dish;
import ru.graduation.repository.DishRepository;
import ru.graduation.repository.RestaurantRepository;

import static ru.graduation.util.validation.ValidationUtil.assureIdConsistent;
import static ru.graduation.util.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    @Transactional
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

    @Transactional
    public void delete(int dishId) {
        dishRepository.deleteExisted(dishId);
    }
}
