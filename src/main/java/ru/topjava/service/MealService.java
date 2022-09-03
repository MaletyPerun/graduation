package ru.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.model.Meal;
import ru.topjava.repository.MealRepository;
import ru.topjava.repository.RestaurantRepository;

import static ru.topjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.topjava.util.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final RestaurantRepository restaurantRepository;
    @Transactional
    public Meal get(int restId, int mealId) {
        return mealRepository.getBelong(restId, mealId);
    }

    @Transactional
    public Meal create(int restId, Meal meal) {
        checkNew(meal);
        meal.setRestaurant(restaurantRepository.getExisted(restId));
        return mealRepository.save(meal);
    }

    @Transactional
    public void update(int restId, Meal meal, int mealId) {
        assureIdConsistent(meal, mealId);
        mealRepository.getBelong(restId, mealId);
        meal.setRestaurant(restaurantRepository.getExisted(restId));
        mealRepository.save(meal);
    }

    @Transactional
    public void delete(int mealId) {
        mealRepository.deleteExisted(mealId);
    }
}
