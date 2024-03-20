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
import java.util.Optional;

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

    public Optional<ReviewDto> getReviewForUserAndRestaurantId( Long userId, Long restaurantId )
    {
        Optional<Review> reviewByUser = reviewRepository.getByUserIdAndRestaurantId( userId, restaurantId );
        if ( reviewByUser.isPresent() )
        {
            List<Rating> byRestaurantId = ratingRepository.findByRestaurant_Id( restaurantId );
            return Optional.of( ReviewDtoMapper.map( reviewByUser.get(), byRestaurantId ) );
        }
        else
        {
            return Optional.empty();
        }
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
        return getReviewDto( reviewDto, review, user, restaurant, save, rating );
    }

    @Transactional
    public ReviewDto updateReviews( long restaurantId, ReviewDto reviewDto, String login )
    {
        Restaurant restaurant = restaurantRepository.findById( restaurantId ).orElseThrow();
        User user = userRepository.findByLogin( login ).orElseThrow();
        Review review = reviewRepository.getByUserIdAndRestaurantId( user.getId(), restaurantId ).orElseThrow();
        review.setUser( user );
        review.setRestaurant( restaurant );
        review.setContent( reviewDto.getReviewContent() );
        Review save = reviewRepository.save( review );

        Rating rating = ratingRepository.getByReviewId( save.getId() ).orElseThrow();
        return getReviewDto( reviewDto, review, user, restaurant, save, rating );
    }

    private ReviewDto getReviewDto( ReviewDto reviewDto, Review review, User user, Restaurant restaurant, Review save, Rating rating )
    {
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
