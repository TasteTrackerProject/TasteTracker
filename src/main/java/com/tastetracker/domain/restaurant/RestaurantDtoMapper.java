package com.tastetracker.domain.restaurant;

import com.tastetracker.domain.category.Category;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import com.tastetracker.entity.restaurantcategory.RestaurantCategory;

import java.util.stream.Collectors;

public class RestaurantDtoMapper
{
    static RestaurantDto map( Restaurant restaurant )
    {
        String categoryName = restaurant
            .getRestaurantCategories()
            .stream()
            .map( RestaurantCategory::getCategory )
            .toList()
            .stream()
            .map( Category::getName )
            .collect( Collectors.joining( "," ) );

        return new RestaurantDto(
            restaurant.getId(),
            restaurant.getName(),
            restaurant.isPromoted(),
            restaurant.getAddress().getStreet(),
            restaurant.getAddress().getCity(),
            restaurant.getAddress().getPostalCode(),
            restaurant.getAddress().getCountry(),
            categoryName
        );
    }
}
