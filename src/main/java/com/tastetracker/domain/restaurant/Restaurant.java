package com.tastetracker.domain.restaurant;

import com.tastetracker.domain.address.Address;
import com.tastetracker.entity.restaurantcategory.RestaurantCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
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

    @OneToMany( fetch = FetchType.EAGER, mappedBy = "restaurant" )
    private Set<RestaurantCategory> restaurantCategories = new HashSet<>();

    private String banner;

    @Column( name = "approved_by_admin" )
    private boolean approved;


    public void addRestaurantCategory( RestaurantCategory restaurantCategory )
    {
        this.restaurantCategories.add( restaurantCategory );
    }
}

