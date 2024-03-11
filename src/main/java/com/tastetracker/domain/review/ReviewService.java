package com.tastetracker.domain.review;

import com.tastetracker.domain.rating.Rating;
import com.tastetracker.domain.rating.RatingRepository;
import com.tastetracker.domain.restaurant.Restaurant;
import com.tastetracker.domain.restaurant.RestaurantRepository;
import com.tastetracker.domain.review.dto.ReviewDto;
import com.tastetracker.domain.user.User;
import com.tastetracker.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService
{
    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;

    private final RatingRepository ratingRepository;

    public List<ReviewDto> getReviewForRestaurant( Long restaurantId )
    {
        List<Review> reviews = reviewRepository.findByRestaurant_IdOrderByCreatedAtDesc( restaurantId );
        return reviews.stream()
            .map( review -> {
                List<Rating> ratings = ratingRepository.findByReviewId( review.getId() );
                return ReviewDtoMapper.map( review, ratings );
            } )
            .toList();
    }

    public boolean hasUserReviewsRestaurant( Long userId, Long restaurantId )
    {
        return reviewRepository.existsByUserIdAndRestaurantId( userId, restaurantId );
    }

    @Transactional
    public ReviewDto addReviews( long id, ReviewDto reviewDto, String login )
    {
        Review review = new Review();
        User user = userRepository.findByLogin( login ).orElseThrow();
        Restaurant restaurant = restaurantRepository.findById( id ).orElseThrow();
        review.setUser( user );
        review.setRestaurant( restaurant );
        review.setContent( reviewDto.getReviewContent() );
        Review save = reviewRepository.save( review );

        Rating rating = new Rating();
        rating.setRatingTaste( reviewDto.getRatingTaste() );
        rating.setRatingAtmosphere( reviewDto.getRatingAtmosphere() );
        rating.setRatingService( reviewDto.getRatingService() );
        rating.setUser( user );
        rating.setRestaurant( restaurant );
        rating.setReview( save );

        ratingRepository.save( rating );
        return ReviewDtoMapper.map( review, rating );
    }
}
