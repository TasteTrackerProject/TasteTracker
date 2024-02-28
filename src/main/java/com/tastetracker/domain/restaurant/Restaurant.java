package com.tastetracker.domain.restaurant;

import com.tastetracker.domain.address.Address;
import com.tastetracker.domain.category.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Restaurant
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String name;
    private boolean promoted;
    @ManyToOne
    @JoinColumn( name = "address_id", referencedColumnName = "id" )
    private Address address;
    @ManyToMany
    @JoinTable(
        name = "restaurant_category",
        joinColumns = @JoinColumn( name = "restaurant_id", referencedColumnName = "id" ),
        inverseJoinColumns = @JoinColumn( name = "category_id", referencedColumnName = "id" )
    )
    private Set<Category> category = new HashSet<>();
}
