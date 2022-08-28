package ru.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.topjava.HasIdAndPlace;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo implements HasIdAndPlace {

    String address;

    boolean chosen;

    public RestaurantTo(Integer id, String name, String address, boolean chosen) {
        super(id, name);
        this.address = address;
        this.chosen = chosen;
    }
}
