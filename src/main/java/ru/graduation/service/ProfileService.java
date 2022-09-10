package ru.graduation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.error.IllegalRequestDataException;
import ru.graduation.model.User;
import ru.graduation.repository.RestaurantRepository;
import ru.graduation.repository.UserRepository;
import ru.graduation.util.Util;

import java.time.LocalTime;

import static ru.graduation.util.validation.ValidationUtil.inTime;

@Service
@AllArgsConstructor
public class ProfileService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public void prepareAndVote(int restId, boolean voteRestaurantId, User user) {
        if (!restaurantRepository.existsById(restId)) {
            throw new IllegalRequestDataException("Entity with id=" + restId + " not found");
        }
        LocalTime time = LocalTime.now();
//        cannot vote 11:00 - 15:00
//        inTime(Util.isBetweenHalfOpen(time));
//        user.setVoteRestaurantId(voteRestaurantId ? restId : 0);
        userRepository.save(user);
    }
}
