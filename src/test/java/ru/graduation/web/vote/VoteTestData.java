package ru.graduation.web.vote;

import ru.graduation.model.Dish;
import ru.graduation.model.Restaurant;
import ru.graduation.model.Vote;
import ru.graduation.web.MatcherFactory;

import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE;
import static ru.graduation.web.restaurant.RestaurantTestData.*;
import static ru.graduation.web.user.UserTestData.admin;
import static ru.graduation.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");

//    public static MatcherFactory.Matcher<Vote> VOTE_WITH_RESTAURANT_AND_USER_MATCHER =
//            MatcherFactory.usingAssertions(Vote.class,
//                    //     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
//                    (a, e) -> assertThat(a).usingRecursiveComparison()
//                            .ignoringFields("user", "restaurant").isEqualTo(e),
//                    (a, e) -> {
//                        throw new UnsupportedOperationException();
//                    });
    public static final int VOTE_ID1 = 1;

//    public static final Vote VOTE_1 = new Vote(VOTE_ID1, user, rest1, LocalDate.now());

    public static final LocalDate localDateTest = LocalDate.now();

    public static Vote getNewVote() {
        return new Vote(null, LocalDate.now());
    }

    public static Vote getUpdatedVote() {
        return new Vote(VOTE_ID1, LocalDate.now());
    }
}
