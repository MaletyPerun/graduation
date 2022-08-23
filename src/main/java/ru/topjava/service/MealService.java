package ru.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.model.Meal;
import ru.topjava.repository.MealRepository;
import ru.topjava.repository.UserRepository;

@Service
@AllArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    @Transactional
    public Meal save(Meal meal, int userId) {
//        meal.setUser(userRepository.getExisted(userId));
        return mealRepository.save(meal);
    }
}
