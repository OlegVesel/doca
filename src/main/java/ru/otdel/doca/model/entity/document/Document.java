package ru.otdel.doca.model.entity.document;

import jakarta.persistence.*;
import lombok.Data;
import ru.otdel.doca.model.entity.BaseEntity;
import ru.otdel.doca.model.entity.dictionary.TypeDoc;

import java.util.List;

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

    @ManyToMany(mappedBy = "documents", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Card> cards;
}
