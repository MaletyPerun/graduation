package ru.topjava.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import java.time.LocalTime;

@UtilityClass
public class Util {

    private static final LocalTime START_OF_LANCH = LocalTime.of(11, 0);
    private static final LocalTime END_OF_LANCH = LocalTime.of(15, 0);

    public static boolean isBetweenHalfOpen (LocalTime timeOfChoose) {
        return (timeOfChoose.compareTo(START_OF_LANCH) >= 0 && timeOfChoose.compareTo(END_OF_LANCH) < 0);
    }
}