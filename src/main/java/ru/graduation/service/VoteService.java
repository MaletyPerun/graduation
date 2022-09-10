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

import static ru.graduation.util.validation.ValidationUtil.inTime;

@Service
@AllArgsConstructor
public class VoteService {

    private static final LocalTime END_TIME_TO_REVOTE = LocalTime.of(11, 0);

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    public Vote create(AuthUser authUser, int restId, Vote vote) {
        return voteRepository.save(prepareToSave(vote, authUser.getUser(), restId));
    }

    public void update(AuthUser authUser, int restId, Vote vote) {
        LocalDateTime revote = LocalDateTime.now();
        voteRepository.getBelong(authUser.getUser().getId(), revote.toLocalDate());
        inTime(revote.toLocalTime().isBefore(END_TIME_TO_REVOTE));
        voteRepository.save(prepareToSave(vote, authUser.getUser(), restId));
    }

    public Vote prepareToSave(Vote vote, User user, int restId) {
        vote.setUser(user);
        vote.setRestaurant(restaurantRepository.getExisted(restId));
        return vote;
    }
}
