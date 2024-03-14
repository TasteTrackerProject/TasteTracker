package com.tastetracker.web.api;

import com.tastetracker.domain.restaurant.Restaurant;
import com.tastetracker.domain.restaurant.RestaurantService;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import lombok.AllArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/search")
@AllArgsConstructor
public class RestaurantRestController {
    private RestaurantService restaurantService;

    @GetMapping(params = "name")
    public Iterable<RestaurantDto> get(
            @Join(path = "category", alias = "c")
            @Join(path = "address", alias = "a")
            @Or({
                    @Spec(path = "c.name", params = "name", spec = LikeIgnoreCase.class),
                    @Spec(path = "name", params = "name", spec = LikeIgnoreCase.class),
                    @Spec(path = "a.city", params = "name", spec = LikeIgnoreCase.class)
            }) Specification<Restaurant> spec
    ) {
        return restaurantService.findAllRestaurants(spec);
    }
}
