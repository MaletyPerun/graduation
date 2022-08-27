package ru.topjava.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo {

    String address;

    boolean chosen;

    public RestaurantTo(Integer id, String name, String address, boolean chosen) {
        super(id, name);
        this.address = address;
        this.chosen = chosen;
    }
}
