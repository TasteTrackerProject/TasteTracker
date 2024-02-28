package com.tastetracker.domain.restaurant;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long>
{
    List<Restaurant> findAllByPromotedIsTrue();
    List<Restaurant> findAllByCategory_NameIgnoreCase(String name);
}
