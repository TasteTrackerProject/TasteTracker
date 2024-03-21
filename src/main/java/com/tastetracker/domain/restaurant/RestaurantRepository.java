package com.tastetracker.domain.restaurant;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long>,
        PagingAndSortingRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant>
{
    List<Restaurant> findAllByPromotedIsTrue();
}
