package com.tastetracker.domain.review;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review,Long>
{
    List<Review> findByRestaurant_Id(Long restaurantId);
}
