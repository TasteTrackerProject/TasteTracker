package com.tastetracker.domain.rating;

import com.tastetracker.domain.rating.dto.RatingDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RatingService
{
    private final RatingRepository ratingRepository;

    public Optional<RatingDto> getRatingByRestaurantId( long restaurantId)
    {
        List<Rating> byRestaurantId = ratingRepository.findByRestaurant_Id( restaurantId );
        return RatingDtoMapper.mapToOptional( byRestaurantId );
    }
}
