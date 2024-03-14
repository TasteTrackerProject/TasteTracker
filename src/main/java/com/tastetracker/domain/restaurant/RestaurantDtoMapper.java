package com.tastetracker.domain.restaurant;

import com.tastetracker.domain.category.Category;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantDtoMapper
{
    static RestaurantDto map( Restaurant restaurant )
    {

        String categoryName = restaurant.getCategory()
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

    static RestaurantDto map( Restaurant restaurant, List<Category> categoryList )
    {

        String categoryName = categoryList
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
