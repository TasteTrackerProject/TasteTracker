package com.tastetracker.domain.category;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long>
{
    Optional<Category> findByNameIgnoreCase( String name );
    List<Category> findByRestaurantId(long id);
    boolean existsByName( String name );
}
