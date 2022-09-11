package ru.graduation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.graduation.model.User;
import ru.graduation.model.Vote;
import ru.graduation.repository.RestaurantRepository;
import ru.graduation.repository.VoteRepository;
import ru.graduation.web.AuthUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.graduation.util.validation.ValidationUtil.checkDuplicate;
import static ru.graduation.util.validation.ValidationUtil.inTime;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    public Vote create(AuthUser authUser, int restId, Vote vote) {
        vote.setUser(authUser.getUser());
        vote.setRestaurant(restaurantRepository.getExisted(restId));
        return voteRepository.save(vote);
    }

    public void update(AuthUser authUser, int restId, Vote vote) {
        LocalDateTime revote = LocalDateTime.now();
        Vote fromDB = voteRepository.getBelong(authUser.getUser().getId(), revote.toLocalDate());
        checkDuplicate(fromDB.getRestaurant().getId(), restId);
        inTime(revote);
        vote.setUser(authUser.getUser());
        vote.setRestaurant(restaurantRepository.getExisted(restId));
        voteRepository.save(vote);
    }
}
