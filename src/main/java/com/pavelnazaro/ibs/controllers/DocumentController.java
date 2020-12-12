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
    public Document saveNewDocument(
            @RequestParam(name = "first_company") String firstCompany,
            @RequestParam(name = "second_company") String secondCompany
    ){
        return documentService.saveDocument(new Document(firstCompany, secondCompany));
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
                throw new SignedErrorException("Already signed");
            }
            document.setFirstSignature(true);
        } else if (company.equals(document.getSecondCompany())) {
            if (document.getFirstSignature()){
                if (document.getSecondSignature()){
                    throw new SignedErrorException("Already signed");
                }
                document.setSecondSignature(true);
            } else {
                throw new SignedErrorException("The company in which the document was created is signed first");
            }
        }

        return documentService.saveDocument(document);
    }

//    @GetMapping("/sign_document")
//    @ResponseBody
//    public Document signDocument(
//            @RequestParam(name = "id") Long id,
//            @RequestParam(name = "first_company", required = false) String firstCompany,
//            @RequestParam(name = "second_company", required = false) String secondCompany
//    ){
//        Document document = documentService.findDocument(id).orElseThrow(DocumentNotFoundException::new);
//
//        if (firstCompany == null && secondCompany == null) {
//            throw new SignedErrorException("Check the first or second company");
//        }
//        if (firstCompany != null && secondCompany != null){
//            throw new SignedErrorException("There must be either the first or the second company");
//        }
//        if (firstCompany != null) {
//            document.setFirstSignature(true);
//        }
//        if (secondCompany != null) {
//            if (document.getFirstSignature()){
//                document.setSecondSignature(true);
//            } else {
//                throw new SignedErrorException("The company in which the document was created is signed first");
//            }
//        }
//
//        return documentService.saveDocument(document);
//    }

    @GetMapping("/delete_document")
    @ResponseBody
    public String deleteDocument(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "company_name") String companyName
    ){
        Document document = documentService.findDocument(id).orElseThrow(DocumentNotFoundException::new);
        if (document.getFirstCompany().equals(companyName)){
            if (document.getFirstSignature() && !document.getSecondSignature()) {
                throw new DeleteDocumentErrorException("Because the second side has not signed the document");
            }
            documentService.deleteDocument(document);
        } else {
            throw new DeleteDocumentErrorException("Only the company that created the document can delete it");
        }

        return "Delete ok";
    }
}
