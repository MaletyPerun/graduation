package ru.javaops.topjava.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Meal;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {

//    @Query("SELECT m FROM Meal m ORDER BY m.price DESC")
//    List<Meal> getAll(int userId);
//
//    @Query("SELECT m from Meal m WHERE m.user.id=:userId AND m.dateTime >= :startDate AND m.dateTime < :endDate ORDER BY m.dateTime DESC")
//    List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId);
//
//    @Query("SELECT m FROM Meal m WHERE m.id = :id and m.user.id = :userId")
//    Optional<Meal> get(int id, int userId);
//
//    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id = :id and m.user.id = :userId")
//    Optional<Meal> getWithUser(int id, int userId);
//
//    default Meal checkBelong(int id, int userId) {
//        return get(id, userId).orElseThrow(
//                () -> new DataConflictException("Meal id=" + id + " doesn't belong to User id=" + userId));
//    }
}