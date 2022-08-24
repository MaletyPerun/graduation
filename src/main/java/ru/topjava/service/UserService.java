package ru.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.model.Restaurant;
import ru.topjava.model.User;
import ru.topjava.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class UserService {
    private final RestaurantRepository restaurantRepository;
//    private final UserRepository userRepository;

    @Transactional
    public User prepareAndChoose(int restId, boolean choose, User user) {
        Restaurant restaurant = restaurantRepository.getExisted(restId);
//        if (time < 11:00)
        if (choose) {
            user.setChoose(restId);
        } else {
            user.setChoose(0);
        }
//        if (restId == user.getChoose())
//            user.setChoose(restId);
        return user;
    }
}
