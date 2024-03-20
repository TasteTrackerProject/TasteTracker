package com.tastetracker.domain.category;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository
    extends CrudRepository<Category, Long>
{
    Optional<Category> findByNameIgnoreCase( String name );

    boolean existsByName( String name );
}
