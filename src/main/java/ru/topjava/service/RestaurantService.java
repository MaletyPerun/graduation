package ru.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.topjava.model.Restaurant;
import ru.topjava.model.User;
import ru.topjava.repository.RestaurantRepository;
import ru.topjava.to.RestaurantTo;
import ru.topjava.util.RestaurantUtil;

import java.util.List;
import java.util.Optional;

import static ru.topjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.topjava.util.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;

    public List<RestaurantTo> getAll(User user) {
        return RestaurantUtil.getTos(repository.getAll(), user.getVoteIdRestaurant());
    }

    public Optional<Restaurant> get(int restId) {
        return repository.findById(restId);
    }

    public Optional<Restaurant> getWithMeals(int restId) {
        return repository.getWithMeals(restId);
    }

    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        return repository.save(restaurant);
    }

    public void update(Restaurant restaurant, int restId) {
        assureIdConsistent(restaurant, restId);
        repository.save(restaurant);
    }

    public void delete(int restId) {
        repository.deleteExisted(restId);
    }
}
