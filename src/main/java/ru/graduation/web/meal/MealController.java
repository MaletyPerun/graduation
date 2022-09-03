package ru.graduation.web.meal;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.model.Meal;
import ru.graduation.service.MealService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = MealController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "meals")
public class MealController {

    static final String REST_URL = "/api/restaurants/{restId}/meals";

    private final MealService service;

    @GetMapping("/{mealId}")
    @Cacheable
    public ResponseEntity<Meal> get(@PathVariable int restId, @PathVariable int mealId) {
        log.info("get meal {} of restaurant {}", mealId, restId);
        return ResponseEntity.ok(service.get(restId, mealId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    @Transactional
    public ResponseEntity<Meal> create(@PathVariable int restId, @Valid @RequestBody Meal meal) {
        log.info("create {} of restaurant {}", meal, restId);
        Meal created = service.create(restId, meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/restaurants/" + restId + "/meals/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Transactional
    public void update(@PathVariable int restId, @Valid @RequestBody Meal meal, @PathVariable int mealId) {
        log.info("update {} for restaurant {}", meal, restId);
        service.update(restId, meal, mealId);
    }

    @DeleteMapping("/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int restId, @PathVariable int mealId) {
        log.info("delete meal {} of Restaurant {}", mealId, restId);
        service.delete(mealId);
    }
}