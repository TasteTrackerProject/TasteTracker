package com.tastetracker.domain.restaurant.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class NewRestaurantDto
{
    private String name;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private MultipartFile banner;
    private String categories;
}
