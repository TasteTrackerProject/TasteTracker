package com.tastetracker.domain.restaurant.dto;


import java.util.Map;

public record RestaurantDto(Long id,
                            String name,
                            boolean promoted,
                            String street,
                            String city,
                            String postalCode,
                            String country,
                            String banner,
                            String category,
                            Map<String,String> openingHours)
{
}
