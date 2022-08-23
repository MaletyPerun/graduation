package ru.javaops.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javaops.topjava.repository.MealRepository;
import ru.javaops.topjava.repository.UserRepository;

@Service
@AllArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;

//    @Transactional
//    public Meal save(Meal meal, int userId) {
//        meal.setUser(userRepository.getExisted(userId));
//        return mealRepository.save(meal);
//    }
}
