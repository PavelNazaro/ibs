package com.pavelnazaro.ibs.exceptions;

import com.pavelnazaro.ibs.entities.Document;

public class SignedErrorException extends LoggerException {
    public SignedErrorException(String message, Document document, String company) {
        super("Error in signing the document. " + message + ". The company that created the document: '" +
                document.getFirstCompany() + "'. Company who wants to sign a document: '" + company + "'");
    }
}
