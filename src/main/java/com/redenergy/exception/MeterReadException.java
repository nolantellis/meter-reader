package com.redenergy.exception;

/**
 * 
 * @author Nolan.Tellis
 *         This Exception class is thrown when there is an error while reading
 *         meters
 */
public class MeterReadException extends Exception
{
    public MeterReadException(String message)
    {
        super(message);
    }

}
