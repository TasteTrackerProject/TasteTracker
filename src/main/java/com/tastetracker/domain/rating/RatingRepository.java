package com.tastetracker.domain.rating;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends CrudRepository<Rating, Long>
{
    List<Rating> findByRestaurant_Id( Long restaurantId);
    List<Rating> findByReviewId(Long reviewId);
    Optional<Rating> getByReviewId(Long reviewId);
}
