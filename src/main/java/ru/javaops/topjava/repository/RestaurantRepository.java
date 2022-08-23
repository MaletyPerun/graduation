package ru.javaops.topjava.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Meal;
import ru.javaops.topjava.model.Restaurant;
import ru.javaops.topjava.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT m FROM Meal m ORDER BY m.price DESC")
    List<Restaurant> getAll();

    @Query("SELECT m from Meal m WHERE m.user.id=:userId AND m.dateTime >= :startDate AND m.dateTime < :endDate ORDER BY m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId);

    @Query("SELECT m FROM Meal m WHERE m.id = :id and m.user.id = :userId")
    Optional<Meal> get(int id, int userId);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id = :id and m.user.id = :userId")
    Optional<Meal> getWithUser(int id, int userId);

    default Meal checkBelong(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Meal id=" + id + " doesn't belong to User id=" + userId));
    }

    @EntityGraph(attributePaths = {"meals"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u WHERE u.id=?1")
    Optional<User> getWithMeals(int id);
}