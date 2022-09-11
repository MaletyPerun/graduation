package ru.graduation.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Dish;
import ru.graduation.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static ru.graduation.util.validation.ValidationUtil.checkBelong;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.dateVote=:dateVote")
    Vote getVoteByUserAndLocalDate(int userId, @Param("dateVote") LocalDate dateVote);

    default Vote getBelong(int userId, LocalDate localDate) {
        return checkBelong(getVoteByUserAndLocalDate(userId, localDate), userId, localDate);
    }
}