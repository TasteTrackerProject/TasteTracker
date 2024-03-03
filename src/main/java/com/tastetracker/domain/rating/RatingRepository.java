package com.tastetracker.domain.rating;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating, Long>
{
    List<Rating> findByRestaurant_Id( Long restaurantId);
}
