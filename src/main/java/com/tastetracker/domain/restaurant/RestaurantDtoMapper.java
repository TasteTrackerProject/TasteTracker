package com.tastetracker.domain.restaurant;

import com.tastetracker.domain.restaurant.dto.RestaurantDto;

public class RestaurantDtoMapper
{
    static RestaurantDto map( Restaurant restaurant )
    {
        return new RestaurantDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.isPromoted(),
                restaurant.getAddress().getStreet(),
                restaurant.getAddress().getCity(),
                restaurant.getAddress().getPostalCode(),
                restaurant.getAddress().getCountry()
        );
    }
}
