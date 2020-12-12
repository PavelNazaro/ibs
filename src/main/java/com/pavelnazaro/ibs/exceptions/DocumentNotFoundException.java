package com.pavelnazaro.ibs.exceptions;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException() {
        super("Document not found!");
    }
}
