package ru.graduation.web.restaurant;

import ru.graduation.model.Restaurant;
import ru.graduation.to.RestaurantTo;
import ru.graduation.web.MatcherFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);
    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MEALS_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                    //     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("meals").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int REST_ID1 = 1;
    public static final int REST_ID2 = 2;
    public static final int REST_ID3 = 3;
    public static final int NOT_FOUND = 100;

    public static final Restaurant rest1 = new Restaurant(REST_ID1, "McDonalds", "Пушкинская 8");
    public static final Restaurant rest2 = new Restaurant(REST_ID2, "Все хорошо", "Ломоносовская 17");
    public static final Restaurant rest3 = new Restaurant(REST_ID3, "Доски", "Московский 76");

    public static Restaurant getNew() {
        return new Restaurant(null, "New", "newAddress 56");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(REST_ID1, "UpdatedName", "updateAddress 56");
    }
}