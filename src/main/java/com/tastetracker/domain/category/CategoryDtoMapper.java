package com.tastetracker.domain.category;

import com.tastetracker.domain.category.dto.CategoryDto;
import com.tastetracker.domain.restaurant.Restaurant;
import com.tastetracker.entity.restaurantcategory.RestaurantCategory;

import java.util.stream.Collectors;

public class CategoryDtoMapper
{
    static CategoryDto map( Category category )
    {
        return new CategoryDto(
            category.getId(),
            category.getName(),
            category.getRestaurantCategories()
                .stream()
                .map( RestaurantCategory::getRestaurant )
                .map( Restaurant::getName )
                .collect( Collectors.toSet() ),
            category.getBanner()
        );
    }
}
