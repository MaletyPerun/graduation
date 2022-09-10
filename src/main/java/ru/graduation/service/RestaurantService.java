package ru.graduation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.graduation.model.Restaurant;
import ru.graduation.model.User;
import ru.graduation.repository.RestaurantRepository;
import ru.graduation.to.RestaurantTo;
import ru.graduation.util.RestaurantUtil;

import java.util.List;
import java.util.Optional;

import static ru.graduation.util.validation.ValidationUtil.assureIdConsistent;
import static ru.graduation.util.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    public Optional<Restaurant> get(int restId) {
        return repository.findById(restId);
    }

    public Optional<Restaurant> getWithMeals(int restId) {
        return repository.getWithMeals(restId);
    }

    public Restaurant create(Restaurant restaurant) {
        return repository.save(restaurant);
    }

    public void update(Restaurant restaurant, int restId) {
        repository.save(restaurant);
    }

    public void delete(int restId) {
        repository.deleteExisted(restId);
    }
}
