package ru.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.model.Meal;
import ru.topjava.model.Restaurant;
import ru.topjava.repository.MealRepository;
import ru.topjava.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Meal save(Meal meal, int restId) {
        meal.setRestaurant(restaurantRepository.getExisted(restId));
        return mealRepository.save(meal);
    }

    @Transactional
    public Restaurant getRestaurant(int id) {
        return restaurantRepository.getReferenceById(id);
    }
}
