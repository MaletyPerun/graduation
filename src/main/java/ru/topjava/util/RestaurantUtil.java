package ru.topjava.util;

import lombok.experimental.UtilityClass;
import ru.topjava.model.Meal;
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
                .map(restaurant -> {
                    assert restaurant.getId() != null;
                    return creatTo(restaurant, restaurant.getId().equals(restId));
                })
                .collect(Collectors.toList());
    }

//    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
//        return filterByPredicate(meals, caloriesPerDay, meal -> true);
//    }
//
//    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
//        return filterByPredicate(meals, caloriesPerDay, meal -> Util.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
//    }
//
//    public static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
//        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
//                .collect(
//                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
////                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
//                );
//
//        return meals.stream()
//                .filter(filter)
//                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
//                .collect(Collectors.toList());
//    }
//
//    public static MealTo createTo(Meal meal, boolean excess) {
//        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
//    }
}
