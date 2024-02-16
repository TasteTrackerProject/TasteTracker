package com.tastetracker.domain.restaurant;

import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public Optional<RestaurantDto> findByRestaurantId( long id )
    {
        return restaurantRepository.findById( id ).map( RestaurantDtoMapper::map );
    }
}
