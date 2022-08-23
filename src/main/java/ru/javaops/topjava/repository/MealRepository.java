package ru.javaops.topjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restId ORDER BY m.price DESC")
    List<Meal> getAll(int restId);


    default Meal checkBelong(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Meal id=" + id + " doesn't belong to User id=" + userId));
    }
}