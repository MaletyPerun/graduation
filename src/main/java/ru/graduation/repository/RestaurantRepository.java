package ru.graduation.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Restaurant;

import java.util.Collection;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT r FROM Restaurant r ORDER BY r.name")
    Collection<Restaurant> getAll();
    // TODO: 10/09/2022 убрать RedstaurantTo 

//   https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"meals"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes WHERE r.id = :restId")
    Optional<Restaurant> getWithMeals(int restId);
}