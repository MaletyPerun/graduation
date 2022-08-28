package ru.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.topjava.util.validation.NoHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {

    @NotBlank
    @Size(min = 2, max = 120)
    @NoHtml
    String address;

    boolean chosen;

    public RestaurantTo(Integer id, String name, String address, boolean chosen) {
        super(id, name);
        this.address = address;
        this.chosen = chosen;
    }
}
