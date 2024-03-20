package com.tastetracker.entity.restaurantcategory;

import com.tastetracker.domain.category.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RestaurantCategoryRepository
    extends CrudRepository<RestaurantCategory, Long>
{
    List<RestaurantCategory> findAllByCategory( Category category );
}
