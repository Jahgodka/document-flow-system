package com.portfolio.document_flow.controller;

import com.portfolio.document_flow.entity.Document;
import com.portfolio.document_flow.repository.DocumentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("http://localhost:4200")
public class DocumentController {

    private final DocumentRepository documentRepository;

    public DocumentController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @GetMapping("/status")
    public String getStatus() {
        return "API obiegu dokumentow dziala poprawnie i czeka na Angulara.";
    }

    @GetMapping
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Document getDocumentById(@PathVariable Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Document createDocument(@RequestBody Document document) {
        document.setStatus("NOWY");
        return documentRepository.save(document);
    }
}