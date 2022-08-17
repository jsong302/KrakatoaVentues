package com.carta.krakatoa.error;

/**
 * Exception class specific to exceptions in the program
 *
 * @author js
 * @version 1.0
 *
 */
public class CartaException extends Exception{

    /**
     * Constructs a CartaException with an error Message
     *
     * @param errorMessage - The error message
     */
    public CartaException(String errorMessage) {
        super(errorMessage);
    }
}
