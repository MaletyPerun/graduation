package ru.topjava.web.restaurant;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.model.Restaurant;
import ru.topjava.model.User;
import ru.topjava.repository.RestaurantRepository;
import ru.topjava.repository.UserRepository;
import ru.topjava.service.RestaurantService;
import ru.topjava.to.RestaurantTo;
import ru.topjava.web.AuthUser;
import ru.topjava.web.user.ProfileController;
import ru.topjava.web.user.UniqueAddressValidator;
import ru.topjava.web.user.UniqueMailValidator;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.topjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.topjava.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    protected final RestaurantRepository repository;

    protected final RestaurantService service;

    protected final UserRepository userRepository;

    @Autowired
    private UniqueAddressValidator addressValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(addressValidator);
    }

    @GetMapping
    public List<RestaurantTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all restaurants");
        return service.getAll(authUser.getUser());
    }

    @GetMapping("/{restId}")
    public ResponseEntity<Restaurant> get(@PathVariable int restId) {
        log.info("get restaurant {}", restId);
        return ResponseEntity.of(repository.findById(restId));
    }

    @GetMapping("/{restId}/meals")
    public ResponseEntity<Restaurant> getWithMeals(@PathVariable int restId) {
        log.info("get restaurant {} with meals", restId);
        return ResponseEntity.of(repository.getWithMeals(restId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
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
//    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int restId) {
        log.info("update {}", restaurant);
        assureIdConsistent(restaurant, restId);
        repository.save(restaurant);
    }

    @DeleteMapping("/{restId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int restId) {
        log.info("delete restaurant {}", restId);
        repository.delete(restId);
    }
}
