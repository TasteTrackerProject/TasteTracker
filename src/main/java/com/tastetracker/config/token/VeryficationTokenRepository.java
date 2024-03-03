package com.tastetracker.config.token;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VeryficationTokenRepository extends CrudRepository<VeryficationToken, Long>
{
    Optional<VeryficationToken> findByToken( String token );
}
