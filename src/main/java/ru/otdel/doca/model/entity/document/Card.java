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

    @Column(name = "title", length = 100)
    private String title;

//    @ManyToOne
//    @JoinColumn(name = "user_login", referencedColumnName = "login")
//    private UserEntity user;

    private String userLogin;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "cards_docs",
            joinColumns = {@JoinColumn(name = "card_id")},
            inverseJoinColumns = {@JoinColumn(name = "doc_id")})
    private List<Document> documents;
}
