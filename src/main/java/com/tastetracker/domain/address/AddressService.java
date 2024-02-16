package com.tastetracker.domain.address;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AddressService
{
    private final AddressRepository addressRepository;

}
