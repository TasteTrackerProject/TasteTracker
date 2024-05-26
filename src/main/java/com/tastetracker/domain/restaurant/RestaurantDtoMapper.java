package com.tastetracker.domain.restaurant;

import com.tastetracker.domain.category.Category;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import com.tastetracker.entity.restaurantcategory.RestaurantCategory;


import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

        Map<String, String> openingSchedule = restaurant.getRestaurantSchedules().stream().collect( Collectors.toMap(
            opening -> opening.getDayOfWeek().getDisplayName( TextStyle.FULL, Locale.of( "PL" ) ),
            opening -> opening.getOpeningTime() + " - " + opening.getClosingTime()
        ) );

        List<String> daysOfWeekInOrder = Arrays.asList(
            "poniedziałek", "wtorek", "środa", "czwartek", "piątek", "sobota", "niedziela"
        );

        Map<String, String> sortedOpeningSchedule = new LinkedHashMap<>();
        daysOfWeekInOrder.forEach(day -> {
            if (openingSchedule.containsKey(day)) {
                sortedOpeningSchedule.put(day, openingSchedule.get(day));
            }
        });


        return new RestaurantDto(
            restaurant.getId(),
            restaurant.getName(),
            restaurant.isPromoted(),
            restaurant.getAddress().getStreet(),
            restaurant.getAddress().getCity(),
            restaurant.getAddress().getPostalCode(),
            restaurant.getAddress().getCountry(),
            restaurant.getBanner(),
            categoryName,
            sortedOpeningSchedule,
            restaurant.getOrderLink());
    }
}
