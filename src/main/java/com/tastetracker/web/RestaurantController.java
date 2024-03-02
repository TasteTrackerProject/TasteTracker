package com.tastetracker.web;

import com.tastetracker.domain.restaurant.RestaurantService;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import com.tastetracker.domain.review.ReviewService;
import com.tastetracker.domain.review.dto.ReviewDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor( access = AccessLevel.PUBLIC )
public class RestaurantController
{

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @GetMapping( "/restaurant/{id}" )
    public String getRestaurant( @PathVariable long id, Model model )
    {
        Optional<RestaurantDto> optionalRestaurant = restaurantService.findByRestaurantId( id );
        List<ReviewDto> reviews = reviewService.getReviewForRestaurant( id );
        model.addAttribute( "reviews",reviews );
        optionalRestaurant.ifPresent( restaurant -> model.addAttribute("restaurant", restaurant ) );
        return "restaurant";
    }
}
