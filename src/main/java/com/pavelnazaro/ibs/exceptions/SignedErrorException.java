package com.pavelnazaro.ibs.exceptions;

public class SignedErrorException extends RuntimeException {
    public SignedErrorException(String message) {
        super("Error in request. " + message);
    }
}
