package com.pavelnazaro.ibs.exceptions;

public class DeleteDocumentErrorException extends RuntimeException {
    public DeleteDocumentErrorException(String message) {
        super(message);
    }
}
