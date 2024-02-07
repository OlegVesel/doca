package ru.otdel.doca.repo.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otdel.doca.model.entity.document.Document;

import java.util.UUID;

@Repository
public interface DocumentRepo extends JpaRepository<Document, UUID> {
}
