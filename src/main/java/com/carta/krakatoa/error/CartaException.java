package com.carta.krakatoa.error;

public class CartaException extends Exception{

    public CartaException(String errorMessage) {
        super(errorMessage);
    }
}
