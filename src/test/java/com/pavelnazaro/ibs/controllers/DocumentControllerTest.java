package com.pavelnazaro.ibs.controllers;

import com.pavelnazaro.ibs.entities.Document;
import com.pavelnazaro.ibs.services.DocumentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DocumentControllerTest {
    @Autowired
    private DocumentController documentController;

    @MockBean
    private DocumentService documentService;

    @Test
    public void saveNewDocument() {
        Long id = 1L;
        Document mockDocument = new Document(id, "First", "Second");

        Mockito.doReturn(mockDocument).when(documentService).saveDocument(mockDocument);

        Document document = documentService.saveDocument(mockDocument);

        Assertions.assertEquals(document, mockDocument);
    }

    @Test
    public void showAllDocuments(){
        Document mockDocument = new Document();
        List<Document> mockList = new ArrayList<>();
        mockList.add(mockDocument);

        Mockito.doReturn(mockList).when(documentService).findAll();

        List<Document> documentList = documentController.showAllDocuments();

        Assertions.assertEquals(mockList, documentList);
    }

    @Test
    public void signDocument() {
        Long id = 1L;
        Document mockDocument = new Document(id, "First", "Second");

        Mockito.doReturn(Optional.of(mockDocument)).when(documentService).findDocument(id);
        Mockito.doReturn(mockDocument).when(documentService).saveDocument(mockDocument);

        Document document = documentController.signDocument(mockDocument.getId(), mockDocument.getFirstSide(), mockDocument.getSecondSide());

        Assertions.assertEquals(mockDocument, document);
    }

    @Test
    public void deleteDocument() {
        Long id = 1L;
        Document mockDocument = new Document(id, "First", "Second");

        Mockito.doReturn(Optional.of(mockDocument)).when(documentService).findDocument(id);

        String str = documentController.deleteDocument(mockDocument.getId(), mockDocument.getFirstSide());

        Assertions.assertEquals("Delete ok", str);
    }
}
