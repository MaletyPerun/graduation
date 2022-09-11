package ru.graduation.web.vote;

import ru.graduation.model.Vote;
import ru.graduation.web.MatcherFactory;

import java.time.LocalDate;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");

    public static final int VOTE_ID1 = 1;

    public static final LocalDate localDateTest = LocalDate.now();

    public static Vote getNewVote() {
        return new Vote(null, LocalDate.now());
    }

    public static Vote getUpdatedVote() {
        return new Vote(VOTE_ID1, LocalDate.now());
    }
}
