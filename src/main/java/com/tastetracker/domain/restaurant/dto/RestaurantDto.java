package com.tastetracker.domain.restaurant.dto;

public record RestaurantDto(Long id,
                            String name,
                            boolean promoted,
                            String street,
                            String city,
                            String postalCode,
                            String country,
                            String banner,
                            String category)
{
}
