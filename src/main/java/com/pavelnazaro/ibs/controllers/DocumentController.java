package com.pavelnazaro.ibs.controllers;

import com.pavelnazaro.ibs.entities.Document;
import com.pavelnazaro.ibs.exceptions.DocumentNotFoundException;
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
    public List<Document> saveNewDocument(){
        documentService.saveNewDocument(new Document("Not signed", "Not signed"));

        return documentService.findAll();
    }

    @GetMapping("/sign_document")
    @ResponseBody
    public List<Document> signDocument(@RequestParam(name = "id") Long id,
                                           @RequestParam(name = "first_side", required = false) String firstSide,
                                           @RequestParam(name = "second_side", required = false) String secondSide){
        Document document = documentService.findDocument(id).orElseThrow
                (() -> new DocumentNotFoundException("Document not found!"));
        if (firstSide != null) {
            document.setFirstSide(firstSide);
        }
        if (secondSide != null) {
            document.setSecondSide(secondSide);
        }

        documentService.updateDocument(document);

        return documentService.findAll();
    }
}
