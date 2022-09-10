package ru.graduation.web.dish;

import ru.graduation.model.Dish;
import ru.graduation.web.MatcherFactory;

import java.util.Date;

public class MealTestData {

    public static final MatcherFactory.Matcher<Dish> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");
    public static final int MEAL_ID1 = 1;
    public static final int MEAL_ID2 = 2;
    public static final int MEAL_ID3 = 3;
    public static final int MEAL_ID4 = 4;
    public static final int MEAL_ID5 = 5;
    public static final int MEAL_ID6 = 6;

    public static final int NOT_FOUND = 100;

    public static final Dish DISH_1 = new Dish(MEAL_ID1, 500, "Завтрак", new Date());
    public static final Dish DISH_2 = new Dish(MEAL_ID2, 600, "Обед", new Date());
    public static final Dish DISH_6 = new Dish(MEAL_ID6, 400, "Ужин", new Date());

    public static Dish getNew() {
        return new Dish(null, 1500, "Ланч", new Date());
    }

    public static Dish getUpdated() {
        return new Dish(MEAL_ID1, 2000, "Бизнес ланч", new Date());
    }
}
