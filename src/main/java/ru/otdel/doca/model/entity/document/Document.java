package ru.otdel.doca.model.entity.document;

import jakarta.persistence.*;
import lombok.Data;
import ru.otdel.doca.model.entity.BaseEntity;
import ru.otdel.doca.model.entity.dictionary.TypeDoc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "documents")
@Data
public class Document extends BaseEntity {
    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "path_to_doc")
    private String pathToDoc;

    @OneToOne
    @JoinColumn(name = "type_doc")
    private TypeDoc typeDoc;

    @Column(name = "data")
    private byte[] data;

    @ManyToMany(mappedBy = "documents")
    private List<Card> cards;

    public void removeCard(Card card){
        this.cards.remove(card);
        card.getDocuments().remove(this);
    }

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", pathToDoc='" + pathToDoc + '\'' +
                ", typeDoc=" + typeDoc +
                ", data=" + Arrays.toString(data) +
                ", cards=" + cards.size() +
                '}';
    }
}
