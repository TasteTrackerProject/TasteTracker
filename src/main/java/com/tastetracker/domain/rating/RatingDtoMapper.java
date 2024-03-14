package com.tastetracker.domain.rating;

import com.tastetracker.domain.rating.dto.RatingDto;

import java.util.List;
import java.util.Optional;

public class RatingDtoMapper
{
    static Optional<RatingDto> mapToOptional ( List<Rating> ratings)
    {

        double avgRatingTaste = ratings
            .stream()
            .mapToInt(Rating::getRatingTaste)
            .average().orElse( 0 );
        double avgRatingAtmosphere = ratings
            .stream()
            .mapToInt(Rating::getRatingAtmosphere)
            .average().orElse( 0 );
        double avgRatingService = ratings
            .stream()
            .mapToInt(Rating::getRatingService)
            .average().orElse( 0 );
        double avgAllRating = (avgRatingTaste + avgRatingAtmosphere + avgRatingService) / 3;
        int countRatings = ratings.size();

        return Optional.of (new RatingDto(
            avgRatingTaste,
            avgRatingAtmosphere,
            avgRatingService,
            avgAllRating,
            countRatings
        ));
    }
}
