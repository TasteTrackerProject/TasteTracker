package com.tastetracker.domain.address;

import com.tastetracker.domain.address.dto.AddressDto;

public class AddressDtoMapper {
    static AddressDto map(Address address){
        return new AddressDto(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountry()
        );
    }
}
