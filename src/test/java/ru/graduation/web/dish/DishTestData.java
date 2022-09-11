package ru.graduation.web.dish;

import ru.graduation.model.Dish;
import ru.graduation.web.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;

public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");
    public static final int DISH_ID1 = 1;
    public static final int DISH_ID2 = 2;
    public static final int DISH_ID6 = 6;

    public static final int NOT_FOUND = 100;

    public static final Dish DISH_1 = new Dish(DISH_ID1, 500, "Завтрак", LocalDate.of(2022, Month.SEPTEMBER, 11));
    public static final Dish DISH_2 = new Dish(DISH_ID2, 600, "Обед", LocalDate.of(2022, Month.SEPTEMBER, 12));
    public static final Dish DISH_6 = new Dish(DISH_ID6, 400, "Ужин", LocalDate.of(2022, Month.SEPTEMBER, 13));

    public static Dish getNew() {
        return new Dish(null, 1500, "Ланч", LocalDate.now());
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID1, 2000, "Бизнес ланч", LocalDate.now());
    }
}
