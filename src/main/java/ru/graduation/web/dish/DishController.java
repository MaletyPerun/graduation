package ru.graduation.web.dish;

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
import ru.graduation.model.Dish;
import ru.graduation.service.DishService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.graduation.util.validation.ValidationUtil.assureIdConsistent;
import static ru.graduation.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "dishes")
public class DishController {

    static final String REST_URL = "/api/restaurants/{restId}/dishes";

    private final DishService service;

    @GetMapping
    public List<Dish> getAll(@PathVariable int restId) {
        log.info("get all meals of restaurant {}", restId);
        return service.getAll(restId);
    }

    @GetMapping("/{dishId}")
    @Cacheable
    public ResponseEntity<Dish> get(@PathVariable int restId, @PathVariable int dishId) {
        log.info("get meal {} of restaurant {}", dishId, restId);
        return ResponseEntity.ok(service.get(restId, dishId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    @Transactional
    public ResponseEntity<Dish> create(@PathVariable int restId, @Valid @RequestBody Dish dish) {
        checkNew(dish);
        log.info("create {} of restaurant {}", dish, restId);
        Dish created = service.create(restId, dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/restaurants/" + restId + "/dishes/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Transactional
    public void update(@PathVariable int restId, @Valid @RequestBody Dish dish, @PathVariable int dishId) {
        assureIdConsistent(dish, dishId);
        log.info("update {} for restaurant {}", dish, restId);
        service.update(restId, dish, dishId);
    }

    @DeleteMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int restId, @PathVariable int dishId) {
        log.info("delete meal {} of Restaurant {}", dishId, restId);
        service.delete(dishId);
    }
}