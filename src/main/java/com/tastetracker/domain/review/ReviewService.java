package com.tastetracker.domain.review;

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

    public List<ReviewDto> getReviewForRestaurant( Long restaurantId )
    {
        return reviewRepository.findByRestaurant_IdOrderByCreatedAtDesc( restaurantId ).stream()
            .map( ReviewDtoMapper::map )
            .toList();
    }

    public boolean hasUserReviewsRestaurant( Long userId, Long restaurantId )
    {
        return reviewRepository.existsByUserIdAndRestaurantId( userId,restaurantId );
    }

    @Transactional
    public void addReviews( long id, ReviewDto reviewDto, String login )
    {
        Review review = new Review();
        User user = userRepository.findByLogin( login ).orElseThrow();
        Restaurant restaurant = restaurantRepository.findById( id ).orElseThrow();
        review.setUser( user );
        review.setRestaurant( restaurant );
        review.setContent( reviewDto.getReviewContent() );
        reviewRepository.save( review );
    }
}
