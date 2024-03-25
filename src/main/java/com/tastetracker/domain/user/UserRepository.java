package com.tastetracker.domain.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>
{
    Optional<User> findByLogin( String login );
    boolean existsByLogin( String login );
    boolean existsByEmail( String email );
}
