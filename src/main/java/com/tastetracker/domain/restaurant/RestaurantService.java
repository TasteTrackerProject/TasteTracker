package com.tastetracker.domain.restaurant;

import com.tastetracker.domain.address.Address;
import com.tastetracker.domain.address.AddressRepository;
import com.tastetracker.domain.restaurant.dto.NewRestaurantDto;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import com.tastetracker.entity.restaurantcategory.RestaurantCategory;
import com.tastetracker.entity.restaurantcategory.RestaurantCategoryRepository;
import com.tastetracker.storage.FileStorageService;
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
    private final AddressRepository addressRepository;
    private final FileStorageService fileStorageService;
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

    public List<RestaurantDto> findAllRestaurantByCategoryName( String name )
    {
        return restaurantRepository.findAllByCategory_NameIgnoreCase( name )
            .stream()
            .map( RestaurantDtoMapper::map )
            .toList();
    }

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

        String categories = restaurantToSave.getCategories();
        Long savedRestaurantId = savedRestaurant.getId();

        addCategoriesToRestaurant( categories, savedRestaurantId );

    }

    private void addCategoriesToRestaurant( String categories, Long savedRestaurant )
    {
        String[] categoriesIds = categories.split( "," );

        for ( int i = 0; i < categoriesIds.length; i++ )
        {
            RestaurantCategory restaurantCategory = new RestaurantCategory();
            restaurantCategory.setCategoryId( Long.parseLong( categoriesIds[i] ) );
            restaurantCategory.setRestaurantId( savedRestaurant );

            restaurantCategoryRepository.save( restaurantCategory );
        }

    }
}
