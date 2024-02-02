package com.tastetracker.domain.restaurant;

import com.tastetracker.domain.address.Address;
import com.tastetracker.domain.category.RestaurantCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean promoted;
    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToMany(mappedBy = "restaurant")
    private Set<RestaurantCategory> restaurantCategory;
}
