package ru.topjava.web.meal;

import ru.topjava.model.Meal;
import ru.topjava.model.Restaurant;
import ru.topjava.web.MatcherFactory;

import static ru.topjava.web.restaurant.RestaurantTestData.*;

public class MealTestData {

    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class, "restaurant");
    public static final int MEAL_ID1 = 1;
    public static final int MEAL_ID2 = 2;
    public static final int MEAL_ID3 = 3;
    public static final int MEAL_ID4 = 4;
    public static final int MEAL_ID5 = 5;
    public static final int MEAL_ID6 = 6;

    public static final int NOT_FOUND = 100;

    public static final Meal meal1 = new Meal(MEAL_ID1, 500, "Завтрак");
    public static final Meal meal2 = new Meal(MEAL_ID2, 600, "Обед");
    public static final Meal meal3 = new Meal(MEAL_ID3, 700, "Ужин");
    public static final Meal meal4 = new Meal(MEAL_ID4, 800, "Завтрак");
    public static final Meal meal5 = new Meal(MEAL_ID5, 900, "Обед");
    public static final Meal meal6 = new Meal(MEAL_ID6, 400, "Ужин");

    public static Meal getNew() {
        return new Meal(null, 1500, "Ланч");
    }

    public static Meal getUpdated() {
        return new Meal(MEAL_ID1, 2000, "Бизнес ланч");
    }
}
