/**
 * Główny kontroler REST zarządzający cyklem życia dokumentów w systemie.
 * Wystawia publiczne API dla frontendu Angularowego.
 */
package com.portfolio.document_flow.controller;

import com.portfolio.document_flow.entity.Document;
import com.portfolio.document_flow.repository.DocumentRepository;
import com.portfolio.document_flow.service.DocumentAnalyzerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("http://localhost:4200")
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final DocumentAnalyzerService documentAnalyzerService;

    public DocumentController(DocumentRepository documentRepository, DocumentAnalyzerService documentAnalyzerService) {
        this.documentRepository = documentRepository;
        this.documentAnalyzerService = documentAnalyzerService;
    }

    @GetMapping
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @PostMapping
    public Document createDocument(@RequestBody Document document) {
        // Przechwytujemy dokument w locie i pytamy AI o status
        documentAnalyzerService.analyzeAndSetStatus(document);

        return documentRepository.save(document);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable Long id) {
        documentRepository.deleteById(id);
    }
}