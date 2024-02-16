package com.tastetracker.domain.address.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AddressDto {
    private Long id;
    private String street;
    private String city;
    private String postalCode;
    private String country;
}
