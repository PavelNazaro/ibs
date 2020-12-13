package com.pavelnazaro.ibs.exceptions;

import com.pavelnazaro.ibs.entities.Document;

public class DeleteDocumentErrorException extends LoggerException {
    public DeleteDocumentErrorException(String message, Document document, String company) {
        super("Error in deleting document. " + message + ". The company that created the document: '" +
                document.getFirstCompany() + "'. Company who wants to delete a document: '" + company + "'");
    }
}
