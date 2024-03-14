package com.tastetracker.domain.review;

import com.tastetracker.domain.rating.Rating;
import com.tastetracker.domain.restaurant.Restaurant;
import com.tastetracker.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table( name = "restaurant_reviews" )
@Data
public class Review
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

    private String content;

    private LocalDateTime createdAt;

    @OneToMany( mappedBy = "review" )
    private Set<Rating> ratings;

    public Review()
    {
        this.createdAt = LocalDateTime.now();
    }
}
