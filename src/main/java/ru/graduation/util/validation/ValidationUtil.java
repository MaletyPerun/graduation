package ru.graduation.util.validation;

import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.graduation.HasId;
import ru.graduation.error.DataConflictException;
import ru.graduation.error.IllegalRequestDataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@UtilityClass
public class ValidationUtil {

    private static final LocalTime END_TIME_TO_REVOTE = LocalTime.of(11, 0);


    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    public static <T> T checkExisted(T obj, int id) {
        if (obj == null) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
        return obj;
    }

    public static <T> T checkBelong(T obj, int restId, int mealId) {
        if (obj == null) {
            throw new IllegalRequestDataException("Dish id=" + mealId + " doesn't belong to Restaurant id=" + restId);
        }
        return obj;
    }

    public static <T> T checkBelong(T obj, int userId, LocalDate localDate) {
        if (obj == null) {
            throw new IllegalRequestDataException("User id=" + userId + " doesn't vote on date " + localDate);
        }
        return obj;
    }

    public static void inTime(LocalDateTime checkTime) {
        LocalTime time = checkTime.toLocalTime();
        if (time.isAfter(END_TIME_TO_REVOTE)) {
            throw new DataConflictException("You can`t revote after 11:00");
        }
    }

    public static void checkDuplicate(int restIdDb, int restId) {
        if (restIdDb == restId) {
            throw new DataConflictException("You have already voted for this restaurant, can`t revote");
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}