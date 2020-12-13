package com.pavelnazaro.ibs.controllers;

import com.pavelnazaro.ibs.entities.Document;
import com.pavelnazaro.ibs.exceptions.DeleteDocumentErrorException;
import com.pavelnazaro.ibs.exceptions.DocumentNotFoundException;
import com.pavelnazaro.ibs.exceptions.SignedErrorException;
import com.pavelnazaro.ibs.services.DocumentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/documents")
public class DocumentController {
    private static final Logger logger = LogManager.getLogger(DocumentController.class);
    private DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    @ResponseBody
    public List<Document> showAllDocuments(){
        return documentService.findAll();
    }

    @GetMapping("/save_new_document")
    @ResponseBody
    public Document saveNewDocument(
            @RequestParam(name = "first_company") String firstCompany,
            @RequestParam(name = "second_company") String secondCompany
    ){
        Document document = documentService.saveDocument(new Document(firstCompany, secondCompany));
        logger.info("Save new document: " + document.toString());
        return document;
    }

    @GetMapping("/sign_document")
    @ResponseBody
    public Document signDocument(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "company") String company
    ){
        Document document = documentService.findDocument(id).orElseThrow(DocumentNotFoundException::new);

        if (company.equals(document.getFirstCompany())) {
            if (document.getFirstSignature()){
                throw new SignedErrorException("The document has already been signed", document, company);
            }
            document.setFirstSignature(true);
        } else if (company.equals(document.getSecondCompany())) {
            if (document.getFirstSignature()){
                if (document.getSecondSignature()){
                    throw new SignedErrorException("The document has already been signed", document, company);
                }
                document.setSecondSignature(true);
            } else {
                throw new SignedErrorException("The company in which the document was created is signed first", document, company);
            }
        }

        logger.info("Company '" + company + "' signed document: " + document.toString());

        return documentService.saveDocument(document);
    }

    @GetMapping("/delete_document")
    @ResponseBody
    public String deleteDocument(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "company") String company
    ){
        Document document = documentService.findDocument(id).orElseThrow(DocumentNotFoundException::new);
        if (document.getFirstCompany().equals(company)){
            if (document.getFirstSignature() && !document.getSecondSignature()) {
                throw new DeleteDocumentErrorException("The second side has not signed the document", document, company);
            }
            documentService.deleteDocument(document);
        } else {
            throw new DeleteDocumentErrorException("Only the company that created the document can delete it", document, company);
        }

        logger.info("Company '" + company + "' delete document: " + document.toString());

        return "Delete ok";
    }
}
