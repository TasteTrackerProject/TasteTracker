package com.tastetracker.web.api;

import com.tastetracker.domain.restaurant.Restaurant;
import com.tastetracker.domain.restaurant.RestaurantService;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api/search" )
@AllArgsConstructor
@Slf4j
public class RestaurantRestController
{
    private RestaurantService restaurantService;
    @RequestMapping( "/all" )
    public Object findByFullNameAndAddress(
        @Conjunction( {
            @Or( { @Spec( path = "name", params = "first", spec = LikeIgnoreCase.class ),
                   @Spec( path = "address.city", params = "first", spec = LikeIgnoreCase.class ),
                   @Spec( path = "restaurantCategories.category.name", params = "first", spec = LikeIgnoreCase.class ) } ),
            @Or( { @Spec( path = "restaurantCategories.category.name", params = "second", spec = LikeIgnoreCase.class ),
                   @Spec( path = "address.city", params = "second", spec = LikeIgnoreCase.class ) } )
        } ) Specification<Restaurant> customerSpec )
    {
        List<RestaurantDto> allRestaurants = restaurantService.findAllRestaurants( customerSpec );
        log.info( allRestaurants.toString() );
        return allRestaurants;
    }
}
