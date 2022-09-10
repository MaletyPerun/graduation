package ru.graduation.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.model.Dish;
import ru.graduation.model.Vote;
import ru.graduation.repository.VoteRepository;
import ru.graduation.service.VoteService;
import ru.graduation.web.AuthUser;
import ru.graduation.web.dish.DishController;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

import static ru.graduation.util.validation.ValidationUtil.assureIdConsistent;
import static ru.graduation.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
//@CacheConfig(cacheNames = "votes")
public class VoteController {

    static final String REST_URL = "/api/vote";

    private VoteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    @CacheEvict(allEntries = true)
    @Transactional
    public ResponseEntity<Vote> create(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote, @RequestParam int restId) {
        log.info("vote user {} of restaurant {}", authUser.getUser(), restId);
        checkNew(vote);
        Vote created = service.create(authUser, restId, vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{voteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @CacheEvict(allEntries = true)
    @Transactional
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote, @RequestParam int restId, @PathVariable int voteId) {
        assureIdConsistent(vote, voteId);
        log.info("revote user {} for restaurant {}", authUser.getUser(), restId);
        service.update(authUser, restId, vote);
    }
}
