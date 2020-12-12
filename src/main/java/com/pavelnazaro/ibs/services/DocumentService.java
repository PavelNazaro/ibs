package com.pavelnazaro.ibs.services;

import com.pavelnazaro.ibs.entities.Document;
import com.pavelnazaro.ibs.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    private DocumentRepository documentRepository;

    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> findAll(){
        return documentRepository.findAll();
    }

    public Document saveDocument(Document document){
        return documentRepository.save(document);
    }

    public Optional<Document> findDocument(Long id){
        return documentRepository.findById(id);
    }

    public void deleteDocument(Document document){
        documentRepository.delete(document);
    }
}
