package com.tastetracker.domain.rating;

import com.tastetracker.domain.restaurant.Restaurant;
import com.tastetracker.domain.review.Review;
import com.tastetracker.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "restaurant_rating" )
@Data
@NoArgsConstructor
public class Rating
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @JoinColumn( name = "user_id" )
    private User user;

    @ManyToOne
    @JoinColumn( name = "restaurant_id" )
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn( name = "review_id" )
    private Review review;

    private int ratingTaste;

    private int ratingAtmosphere;

    private int ratingService;
}
