package com.tastetracker.domain.category;

import com.tastetracker.entity.restaurantcategory.RestaurantCategory;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Category
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;

    private String banner;

    @OneToMany( fetch = FetchType.EAGER, mappedBy = "category" )
    private Set<RestaurantCategory> restaurantCategories = new HashSet<>();
}

