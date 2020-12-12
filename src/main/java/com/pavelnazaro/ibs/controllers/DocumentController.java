package com.pavelnazaro.ibs.controllers;

import com.pavelnazaro.ibs.entities.Document;
import com.pavelnazaro.ibs.exceptions.DeleteDocumentErrorException;
import com.pavelnazaro.ibs.exceptions.DocumentNotFoundException;
import com.pavelnazaro.ibs.exceptions.SignedErrorException;
import com.pavelnazaro.ibs.services.DocumentService;
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
    public Document saveNewDocument(){
        return documentService.saveDocument(new Document());
    }

    @GetMapping("/sign_document")
    @ResponseBody
    public Document signDocument(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "first_side", required = false) String firstSide,
            @RequestParam(name = "second_side", required = false) String secondSide){
        Document document = documentService.findDocument(id).orElseThrow
                (() -> new DocumentNotFoundException("Document not found!"));
        if (firstSide != null) {
            document.setFirstSide(firstSide);
        }
        if (secondSide != null) {
            if (document.getFirstSide().equals("Not signed")){
                throw new SignedErrorException("The company in which the document was created is signed first");
            } else {
                document.setSecondSide(secondSide);
            }
        }

        return documentService.saveDocument(document);
    }

    @GetMapping("/delete_document")
    @ResponseBody
    public String deleteDocument(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "company_name") String companyName){
        Document document = documentService.findDocument(id).orElseThrow
                (() -> new DocumentNotFoundException("Document not found!"));
        if (document.getFirstSide().equals(companyName)){
            documentService.deleteDocument(document);
        } else {
            throw new DeleteDocumentErrorException("Only the company that created the document can delete it");
        }

        return "Delete ok";
    }
}
