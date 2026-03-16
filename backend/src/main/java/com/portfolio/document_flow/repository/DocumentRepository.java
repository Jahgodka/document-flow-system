package com.portfolio.document_flow.repository;

import com.portfolio.document_flow.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}