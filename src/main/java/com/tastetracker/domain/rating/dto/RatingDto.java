package com.tastetracker.domain.rating.dto;

public record RatingDto( double avgRatingTaste,
                         double avgRatingAtmosphere,
                         double avgRatingService,
                         double avgAllRatings,
                         int countRating)
{
}
