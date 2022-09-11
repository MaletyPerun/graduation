package ru.graduation.web.restaurant;

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
import ru.graduation.model.Restaurant;
import ru.graduation.repository.RestaurantRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.graduation.util.validation.ValidationUtil.assureIdConsistent;
import static ru.graduation.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "restaurants")
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    private final RestaurantRepository repository;

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.getAll();
    }

    @GetMapping("/{restId}")
    @Cacheable
    public ResponseEntity<Restaurant> get(@PathVariable int restId) {
        log.info("get restaurant {}", restId);
        return ResponseEntity.of(repository.findById(restId));
    }

    @GetMapping("/{restId}/withDishes")
    @Cacheable
    public ResponseEntity<Restaurant> getWithMeals(@PathVariable int restId) {
        log.info("get restaurant {} with dishes", restId);
        return ResponseEntity.of(repository.getWithMeals(restId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int restId) {
        assureIdConsistent(restaurant, restId);
        log.info("update {}", restaurant);
        repository.save(restaurant);
    }

    @DeleteMapping("/{restId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int restId) {
        log.info("delete restaurant {}", restId);
        repository.deleteExisted(restId);
    }
}
