package com.tastetracker.entity.restaurantcategory;

import com.tastetracker.domain.category.Category;
import com.tastetracker.domain.restaurant.Restaurant;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table( name = "restaurant_category" )
public class RestaurantCategory
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private Category category;
}
