package com.tastetracker.web;

import com.tastetracker.domain.rating.RatingService;
import com.tastetracker.domain.rating.dto.RatingDto;
import com.tastetracker.domain.restaurant.RestaurantService;
import com.tastetracker.domain.restaurant.dto.NewRestaurantDto;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import com.tastetracker.domain.review.ReviewService;
import com.tastetracker.domain.review.dto.ReviewDto;
import com.tastetracker.web.admin.AdministrationPanelController;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor( access = AccessLevel.PUBLIC )
public class RestaurantController
{

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final RatingService ratingService;

    @GetMapping( "restaurant/{id}" )
    public String getRestaurant( @PathVariable long id, Model model, Authentication authentication )
    {
        Optional<RestaurantDto> optionalRestaurant = restaurantService.findByRestaurantId( id );
        List<ReviewDto> reviews = reviewService.getReviewForRestaurant( id );
        Optional<RatingDto> optionalRatings = ratingService.getRatingByRestaurantId( id );

        ReviewDto addReview = new ReviewDto();

        if ( authentication != null )
        {
            String login = authentication.getName();
            addReview.setLogin( login );
        }

        model.addAttribute( "reviews",reviews );
        model.addAttribute( "addReview", addReview );
        optionalRatings.ifPresent( rating -> model.addAttribute("rating", rating ) );
        optionalRestaurant.ifPresent( restaurant -> model.addAttribute("restaurant", restaurant ) );
        return "restaurant";
    }

    @GetMapping( "restaurant/add" )
    public String getRestaurantAddForm( Model model )
    {
        NewRestaurantDto restaurantDto = new NewRestaurantDto();
        model.addAttribute( "restaurantDto", restaurantDto );
        return "restaurant-add-form";
    }

    @PostMapping( "restaurant/add" )
    public String addNewRestaurant( NewRestaurantDto restaurant, RedirectAttributes redirectAttributes )
    {
        restaurantService.addNewRestaurant( restaurant );
        redirectAttributes.addFlashAttribute(
            AdministrationPanelController.NOTIFICATION_ATTRIBUTE,
            "Restauracja %s została dodana! Oczekuj na zatwierdzenie przez dział administracji".formatted( restaurant.getName() )
        );
        return "redirect:/";
    }


}
