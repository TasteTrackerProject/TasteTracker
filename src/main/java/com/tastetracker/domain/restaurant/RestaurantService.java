package com.tastetracker.domain.restaurant;


import com.tastetracker.domain.address.Address;
import com.tastetracker.domain.address.AddressRepository;
import com.tastetracker.domain.category.Category;
import com.tastetracker.domain.category.CategoryRepository;

import com.tastetracker.domain.restaurant.dto.NewRestaurantDto;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import com.tastetracker.entity.restaurantcategory.RestaurantCategory;
import com.tastetracker.entity.restaurantcategory.RestaurantCategoryRepository;
import com.tastetracker.storage.FileStorageService;
import jakarta.transaction.Transactional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor( access = AccessLevel.PUBLIC )
public class RestaurantService
{
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final FileStorageService fileStorageService;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final CategoryRepository categoryRepository;

  
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


    public List<RestaurantDto> findAllRestaurantByCategoryName( String name )
    {
        return restaurantCategoryRepository
            .findAllByCategory( categoryRepository.findByNameIgnoreCase( name ).orElseThrow() )
            .stream()
            .map( RestaurantCategory::getRestaurant )
            .map( RestaurantDtoMapper::map )
            .toList();
    }


    public List<RestaurantDto> findAllRestaurants( Specification<Restaurant> spec ) 
    {
        return restaurantRepository.findAll( spec ).stream().map( RestaurantDtoMapper::map ).distinct().toList();
    }
  
    @Transactional
    public void addNewRestaurant( NewRestaurantDto restaurantToSave )
    {
        Address address = new Address();

        address.setStreet( restaurantToSave.getStreet() );
        address.setCity( restaurantToSave.getCity() );
        address.setPostalCode( restaurantToSave.getPostalCode() );
        address.setCountry( restaurantToSave.getCountry() );

        Address savedAddress = addressRepository.save( address );

        Restaurant restaurant = new Restaurant();

        restaurant.setName( restaurantToSave.getName() );
        restaurant.setAddress( savedAddress );

        if ( restaurantToSave.getBanner() != null && !restaurantToSave.getBanner().isEmpty() )
        {
            String savedImagePath = fileStorageService.saveImage( restaurantToSave.getBanner() );
            restaurant.setBanner( savedImagePath );
        }
        restaurant.setPromoted( false );
        restaurant.setApproved( false );


        Restaurant savedRestaurant = restaurantRepository.save( restaurant );

        addCategoriesToRestaurant( restaurantToSave, savedRestaurant );
    }



    private void addCategoriesToRestaurant( NewRestaurantDto restaurantDto, Restaurant restaurant )
    {
        String[] categoriesIds = restaurantDto.getCategories().split( "," );

        for ( int i = 0; i < categoriesIds.length; i++ )
        {
            Category category = categoryRepository.findById( Long.valueOf( categoriesIds[i] ) )
                .orElseThrow();

            RestaurantCategory restaurantCategory = new RestaurantCategory();

            restaurantCategory.setRestaurant( restaurant );
            restaurantCategory.setCategory( category );

            restaurantCategoryRepository.save( restaurantCategory );

            restaurant.addRestaurantCategory( restaurantCategory );
        }
    }
}
