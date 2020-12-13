package com.pavelnazaro.ibs.exceptions;

public class DocumentNotFoundException extends LoggerException {
    public DocumentNotFoundException() {
        super("Document not found!");
    }
}
