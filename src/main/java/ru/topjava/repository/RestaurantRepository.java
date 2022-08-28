package ru.topjava.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.topjava.model.Meal;
import ru.topjava.model.Restaurant;
import ru.topjava.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r ORDER BY r.name")
    List<Restaurant> getAll();
//
//    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id = :id and m.user.id = :userId")
//    Optional<Meal> getWithUser(int id, int userId);
//
//
    @EntityGraph(attributePaths = {"meals"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.meals WHERE r.id = :restId")
    Optional<Restaurant> getWithMeals(int restId);

//    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restId ORDER BY m.price DESC")
//    List<Meal> getAll(int restId);

    @Query("SELECT r FROM Restaurant r WHERE r.name =:name AND r.address =:address")
    Optional<Restaurant> findByAddress(String name, String address);

//    @Query("SELECT r FROM Restaurant r JOIN FETCH r.meals WHERE r.id = :restId")
//    Optional<Meal> getWithMeals(int restId);
}