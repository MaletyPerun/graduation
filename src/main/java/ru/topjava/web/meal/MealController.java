package ru.topjava.web.meal;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.model.Meal;
import ru.topjava.model.Restaurant;
import ru.topjava.repository.MealRepository;
import ru.topjava.service.MealService;
import ru.topjava.web.AuthUser;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.topjava.util.validation.ValidationUtil.assureIdConsistent;
import static ru.topjava.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MealController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MealController {
    static final String REST_URL = "/api/restaurants/{restId}/meals";

    private final MealRepository repository;
    private final MealService service;

    @GetMapping
    public List<Meal> getAll(@PathVariable int restId) {
        Restaurant restaurant = service.getRestaurant(restId);
        log.info("get all meals of restaurant {}", restaurant);
        return repository.getAll(restaurant.id());
    }

    //    @GetMapping
//    public List<MealTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
//        log.info("getAll for user {}", authUser.id());
//        return MealsUtil.getTos(repository.getAll(authUser.id()), authUser.getUser().getCaloriesPerDay());
//    }

    @GetMapping("/{mealId}")
    public ResponseEntity<Meal> get(@PathVariable int restId, @PathVariable int mealId) {
        Restaurant restaurant = service.getRestaurant(restId);
        log.info("get meal {} of restaurant {}", mealId, restaurant);
        return ResponseEntity.of(repository.get(restId, mealId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> create(@PathVariable int restId, @Valid @RequestBody Meal meal) {
        Restaurant restaurant = service.getRestaurant(restId);
        log.info("create {} of restaurant {}", meal, restaurant);
        checkNew(meal);
        Meal created = service.save(meal, restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restId, @Valid @RequestBody Meal meal, @PathVariable int mealId) {
        log.info("update {} for restaurant {}", meal, restId);
        assureIdConsistent(meal, mealId);
        repository.checkBelong(restId, mealId);
        service.save(meal, restId);
    }

    @DeleteMapping("/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restId, @AuthenticationPrincipal AuthUser authUser, @PathVariable int mealId) {
        log.info("delete meal {} of Restaurant {}", mealId, restId);
        Meal meal = repository.checkBelong(restId, mealId);
        repository.delete(meal);
    }




//    @GetMapping("/filter")
//    public List<MealTo> getBetween(@AuthenticationPrincipal AuthUser authUser,
//                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
//                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
//
//        int userId = authUser.id();
//        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
//        List<Meal> mealsDateFiltered = repository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), userId);
//        return MealsUtil.getFilteredTos(mealsDateFiltered, authUser.getUser().getCaloriesPerDay(), startTime, endTime);
//    }
}