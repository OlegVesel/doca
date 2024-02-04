package ru.otdel.doca.model.entity.document;

import jakarta.persistence.*;
import lombok.Data;
import ru.otdel.doca.model.entity.BaseEntity;
import ru.otdel.doca.model.entity.user.UserEntity;

import java.util.List;

@Entity
@Table(name = "cards")
@Data
public class Card extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_login", referencedColumnName = "login")
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "cards_docs",
            joinColumns = {@JoinColumn(name = "card_id")},
            inverseJoinColumns = {@JoinColumn(name = "doc_id")})
    private List<Document> documents;
}
