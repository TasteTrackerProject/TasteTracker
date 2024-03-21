package com.tastetracker.domain.address;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String street;

    private String city;

    private String postalCode;

    private String country;
}
