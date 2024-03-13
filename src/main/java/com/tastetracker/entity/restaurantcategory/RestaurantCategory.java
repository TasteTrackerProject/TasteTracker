package com.tastetracker.entity.restaurantcategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table( name = "restaurant_category" )
public class RestaurantCategory
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( name = "restaurant_id" )
    private Long restaurantId;
    @Column( name = "category_id" )
    private Long categoryId;
}
