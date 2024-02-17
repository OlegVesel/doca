package ru.otdel.doca.repo.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otdel.doca.model.entity.document.Document;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepo extends JpaRepository<Document, UUID> {

    @Query(value = "DELETE FROM cards_docs WHERE doc_id = :docId returning card_id", nativeQuery = true)
    List<UUID> deleteRelation(@Param("docId") UUID docId);
}
