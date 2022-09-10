package ru.graduation.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends BaseTo {

    LocalDateTime dateTime;

    String description;

    int calories;

    boolean excess;

    public DishTo(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }
}
