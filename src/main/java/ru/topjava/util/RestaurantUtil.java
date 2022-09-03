package ru.topjava.util;

import lombok.experimental.UtilityClass;
import ru.topjava.model.Restaurant;
import ru.topjava.to.RestaurantTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtil {

    public static RestaurantTo creatTo (Restaurant restaurant, boolean choose) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), choose);
    }

    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants, int restId) {
        return restaurants.stream()
                .map(restaurant -> creatTo(restaurant, restaurant.getId() == (restId)))
                .collect(Collectors.toList());
    }
}
