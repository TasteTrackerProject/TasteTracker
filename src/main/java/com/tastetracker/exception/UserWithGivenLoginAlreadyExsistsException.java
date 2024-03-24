package com.tastetracker.exception;

public class UserWithGivenLoginAlreadyExsistsException
    extends Exception
{
    public UserWithGivenLoginAlreadyExsistsException( String message )
    {
        super( message );
    }
}
