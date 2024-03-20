package com.tastetracker.domain.restaurant;

import com.tastetracker.domain.category.CategoryRepository;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import com.tastetracker.entity.restaurantcategory.RestaurantCategory;
import com.tastetracker.entity.restaurantcategory.RestaurantCategoryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RestaurantService
{

    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;

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
        return restaurantCategoryRepository.findAllByCategory( categoryRepository.findByNameIgnoreCase( name ).orElseThrow() )
            .stream()
            .map( RestaurantCategory::getRestaurant )
            .map( RestaurantDtoMapper::map )
            .toList();
    }

    public List<RestaurantDto> findAllRestaurants(Specification<Restaurant> spec){
        return restaurantRepository.findAll(spec).stream().map( RestaurantDtoMapper::map ).distinct().toList();
    }
}
