package com.tastetracker.exception;

public class UserWithGivenEmailAlreadyExsistsException
    extends Exception
{
    public UserWithGivenEmailAlreadyExsistsException( String message )
    {
        super( message );
    }
}
