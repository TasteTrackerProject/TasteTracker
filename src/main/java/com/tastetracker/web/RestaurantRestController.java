package com.tastetracker.web;

import com.tastetracker.domain.rating.RatingService;
import com.tastetracker.domain.restaurant.RestaurantService;
import com.tastetracker.domain.review.ReviewService;
import com.tastetracker.domain.review.dto.ReviewDto;
import com.tastetracker.domain.user.UserService;
import com.tastetracker.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RestaurantRestController
{
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final RatingService ratingService;

    public ResponseEntity<List<ReviewDto>> getReviewsByRestaurantId( @PathVariable Long restaurantId) {
        List<ReviewDto> reviews = reviewService.getReviewForRestaurant(restaurantId);
        return new ResponseEntity<>( reviews, HttpStatus.OK);
    }

    @PostMapping("/addReview")
    public ResponseEntity<ReviewDto> addReview( @RequestParam("id") long id, @RequestBody ReviewDto reviewDto )
    {
        UserDto user = userService.findUserByLogin( reviewDto.getLogin() )
            .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND ) );
//        if ( reviewService.hasUserReviewsRestaurant( user.id(), id ))
//        {
//            return "error";
//        }
        reviewService.addReviews( id, reviewDto, reviewDto.getLogin() );
        return ResponseEntity.ok(reviewDto);
    }
}
