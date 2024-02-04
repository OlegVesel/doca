package ru.otdel.doca.repo.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otdel.doca.model.entity.document.Card;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepo extends JpaRepository<Card, UUID> {

    List<Card> findAllByUserLogin(String login);
}
