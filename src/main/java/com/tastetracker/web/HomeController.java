package com.tastetracker.web;

import com.tastetracker.domain.restaurant.RestaurantService;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor( access = AccessLevel.PUBLIC )
public class HomeController
{

    private final RestaurantService restaurantService;

    @GetMapping
    @RequestMapping("/")
    public String getHome( Model model )
    {
        List<RestaurantDto> allPromotedRestaurants = restaurantService.findAllPromotedRestaurants();
        model.addAttribute( "restaurants", allPromotedRestaurants );
        return "index";
    }
}
