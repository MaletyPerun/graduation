package ru.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.topjava.model.User;
import ru.topjava.repository.RestaurantRepository;
import ru.topjava.to.RestaurantTo;
import ru.topjava.util.RestaurantUtil;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;

    public List<RestaurantTo> getAll(User user) {
        return RestaurantUtil.getTos(repository.getAll(), user.getVoteIdRestaurant());
    }
}
