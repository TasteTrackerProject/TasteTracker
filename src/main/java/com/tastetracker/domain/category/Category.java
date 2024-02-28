package com.tastetracker.domain.category;

import com.tastetracker.domain.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Category
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String name;
    @ManyToMany( mappedBy = "category" )
    private Set<Restaurant> restaurant = new HashSet<>();
}
