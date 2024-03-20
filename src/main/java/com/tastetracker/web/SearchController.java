package com.tastetracker.web;

import com.tastetracker.domain.restaurant.Restaurant;
import com.tastetracker.domain.restaurant.RestaurantService;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SearchController
{
    private final RestaurantService restaurantService;

    @GetMapping( "/all" )
    public String findByFullNameAndAddress(
        @Conjunction( {
            @Or( { @Spec( path = "name", params = "search", spec = LikeIgnoreCase.class ),
                   @Spec( path = "address.city", params = "search", spec = LikeIgnoreCase.class ),
                   @Spec( path = "restaurantCategories.category.name", params = "search", spec = LikeIgnoreCase.class ) } ),
            @Or( { @Spec( path = "restaurantCategories.category.name", params = "second", spec = LikeIgnoreCase.class ),
                   @Spec( path = "address.city", params = "second", spec = LikeIgnoreCase.class ) } )
        } ) Specification<Restaurant> customerSpec,
        Model model )
    {
        List<RestaurantDto> allRestaurants = restaurantService.findAllRestaurants( customerSpec );
        log.debug( allRestaurants.toString() );
        model.addAttribute( "restaurants", allRestaurants );
        return "restaurant-listing";
    }
}
