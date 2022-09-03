package ru.graduation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import ru.graduation.util.validation.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "meal", uniqueConstraints = {@UniqueConstraint(columnNames = {"price", "description", "restaurant_id"}, name = "meal_unique_restaurant_price_descrip_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meal extends BaseEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 10)
    @NotNull
    private Integer price;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    @NoHtml
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Meal(Integer id, Integer price, String description) {
        super(id);
        this.price = price;
        this.description = description;
    }
}
