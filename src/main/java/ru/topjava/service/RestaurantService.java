package ru.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.model.Restaurant;
import ru.topjava.model.User;
import ru.topjava.repository.RestaurantRepository;
import ru.topjava.repository.UserRepository;
import ru.topjava.to.RestaurantTo;
import ru.topjava.util.RestaurantUtil;
import ru.topjava.util.Util;

import java.time.LocalTime;
import java.util.List;

import static ru.topjava.util.validation.ValidationUtil.inTime;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;


    public List<RestaurantTo> getAll(User user) {
        return RestaurantUtil.getTos(repository.getAll(), user.getChoose());
    }
}
