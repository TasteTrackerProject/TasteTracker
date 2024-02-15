package com.tastetracker.domain.restaurant.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access= AccessLevel.PUBLIC)
public class RestaurantDto {
    private Long id;
    private String name;
    private boolean promoted;
    private String street;
    private String city;
    private String postalCode;
    private String country;
}
