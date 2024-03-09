package ru.otdel.doca.model.entity.document;

import jakarta.persistence.*;
import lombok.Data;
import ru.otdel.doca.model.entity.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards")
@Data

public class Card extends BaseEntity {

    @Column(name = "title", length = 100)
    private String title;
    private String userLogin;
    private String comment;
    private LocalDate executeTo;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "cards_docs",
            joinColumns = {@JoinColumn(name = "card_id")},
            inverseJoinColumns = {@JoinColumn(name = "doc_id")})
    private List<Document> documents = new ArrayList<>();

    @Override
    public String toString() {
        return "Card{" +
                "title='" + title + '\'' +
                ", userLogin='" + userLogin + '\'' +
                ", comment='" + comment + '\'' +
                ", executeTo=" + executeTo +
                ", documents=" + documents.size() +
                '}';
    }
}