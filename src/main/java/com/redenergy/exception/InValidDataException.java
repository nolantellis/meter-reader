package com.redenergy.exception;

/**
 * 
 * @author Nolan.Tellis
 *
 *         This is a runtime exception class which is of a generic type.
 *         This is thrown from any invalid conditions in application.
 */
public class InValidDataException extends RuntimeException
{
    String message;

    public InValidDataException(String message)
    {
        super(message);
        this.message = message;
    }
}
