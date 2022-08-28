package ru.topjava.web.user;

import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import ru.topjava.HasIdAndEmail;
import ru.topjava.model.Restaurant;
import ru.topjava.repository.RestaurantRepository;
import ru.topjava.repository.UserRepository;
import ru.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UniqueAddressValidator implements org.springframework.validation.Validator {
    public static final String EXCEPTION_DUPLICATE_ADDRESS = "This place already exists";

    private final RestaurantRepository repository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Restaurant.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Restaurant restaurant = ((Restaurant) target);
        if (StringUtils.hasText(restaurant.getAddress()) && StringUtils.hasText(restaurant.getName())) {
            repository.findByAddress(restaurant.getName(), restaurant.getAddress())
                    .ifPresent(dbRest -> {
                        if (request.getMethod().equals("PUT")) {  // UPDATE
                            int dbId = dbRest.id();

                            // it is ok, if update ourself
                            if (restaurant.getId() != null && dbId == restaurant.id()) return;

                            // Workaround for update with user.id=null in request body
                            // ValidationUtil.assureIdConsistent called after this validation
                            String requestURI = request.getRequestURI();
                            if (requestURI.endsWith("/" + dbId) || (dbId == SecurityUtil.authId() && requestURI.contains("/profile")))
                                return;
                        }
                        errors.rejectValue("name and address", "", EXCEPTION_DUPLICATE_ADDRESS);
                    });
        }
    }
}
