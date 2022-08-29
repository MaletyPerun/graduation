package ru.topjava.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.error.IllegalRequestDataException;
import ru.topjava.model.User;
import ru.topjava.repository.RestaurantRepository;
import ru.topjava.repository.UserRepository;
import ru.topjava.util.Util;

import java.time.LocalTime;

import static ru.topjava.util.validation.ValidationUtil.inTime;

@Service
@AllArgsConstructor
public class UserService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public void prepareAndVote(int restId, boolean voteIdRestaurant, User user) {
        if (!restaurantRepository.existsById(restId)) {
            throw new IllegalRequestDataException("Entity with id=" + restId + " not found");
        }
        LocalTime time = LocalTime.now();
//        check 11:00 - 15:00
        inTime(Util.isBetweenHalfOpen(time));
        user.setVoteIdRestaurant(voteIdRestaurant ? restId : 0);
        userRepository.save(user);
    }
}
