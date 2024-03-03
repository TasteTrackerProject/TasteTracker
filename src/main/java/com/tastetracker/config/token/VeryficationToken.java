package com.tastetracker.config.token;

import com.tastetracker.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@Table( name = "veryfication_token" )
public class VeryficationToken
{
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String token;
    @OneToOne( targetEntity = User.class, fetch = FetchType.EAGER )
    @JoinColumn( nullable = false, name = "user_id" )
    private User user;
    private Date expiryDate;

    private Date calculateExpiryDate( int expiryTimeInMinutes )
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime( new Timestamp( cal.getTime().getTime() ) );
        cal.add( Calendar.MINUTE, expiryTimeInMinutes );
        return new Date( cal.getTime().getTime() );
    }
}
