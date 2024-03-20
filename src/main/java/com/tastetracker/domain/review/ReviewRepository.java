package com.tastetracker.domain.review;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository
    extends CrudRepository<Review, Long>
{
    List<Review> findByRestaurant_IdOrderByCreatedAtDesc( Long restaurantId );

    boolean existsByUserIdAndRestaurantId( Long userId, Long restaurantId );

    Optional<Review> getByUserIdAndRestaurantId( Long userId, Long restaurantId );
}
