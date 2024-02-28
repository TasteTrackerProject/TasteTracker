package com.tastetracker.domain.restaurant;

import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RestaurantService
{

    private final RestaurantRepository restaurantRepository;

    public Optional<RestaurantDto> findByRestaurantId( long id )
    {
        return restaurantRepository.findById( id ).map( RestaurantDtoMapper::map );
    }

    public List<RestaurantDto> findAllPromotedRestaurants()
    {
        return restaurantRepository.findAllByPromotedIsTrue()
            .stream()
            .map( RestaurantDtoMapper::map )
            .toList();
    }

    public List<RestaurantDto> findAllRestaurantByCategoryName(String name){
        return restaurantRepository.findAllByCategory_NameIgnoreCase( name )
            .stream()
            .map( RestaurantDtoMapper::map )
            .toList();
    }
}
