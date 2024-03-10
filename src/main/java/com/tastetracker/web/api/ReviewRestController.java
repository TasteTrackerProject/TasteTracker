package com.tastetracker.web.api;

import com.tastetracker.domain.rating.RatingService;
import com.tastetracker.domain.rating.dto.RatingDto;
import com.tastetracker.domain.review.ReviewService;
import com.tastetracker.domain.review.dto.ReviewDto;
import com.tastetracker.domain.user.UserService;
import com.tastetracker.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/review")
@AllArgsConstructor
public class ReviewRestController
{

    private final UserService userService;
    private final ReviewService reviewService;
    private final RatingService ratingService;

    @PostMapping( "/{restaurantId}" )
    public ResponseEntity<ReviewDto> addReview( @PathVariable long restaurantId,
                                           @RequestBody ReviewDto reviewDto)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        UserDto user = userService.findUserByLogin( login )
            .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND ) );
                        if ( reviewService.hasUserReviewsRestaurant( user.id(), restaurantId ))
                        {
                            return ResponseEntity.status( HttpStatus.CONFLICT ).build();
                        }
        ReviewDto savedReview = reviewService.addReviews( restaurantId, reviewDto, login );
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/rating/{restaurantId}")
    public ResponseEntity<RatingDto> getRating(@PathVariable long restaurantId){
        RatingDto ratingDto = ratingService.getRatingByRestaurantId( restaurantId )
            .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND ) );
        return ResponseEntity.ok(ratingDto);
    }
    @GetMapping("/checkReviewStatus/{restaurantId}")
    public ResponseEntity<Boolean> checkReviewStatus( @PathVariable long restaurantId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        UserDto user = userService.findUserByLogin( login )
            .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND ) );
        boolean hasUserReviews = reviewService.hasUserReviewsRestaurant( user.id(), restaurantId );
        return ResponseEntity.ok(hasUserReviews);
    }
}
