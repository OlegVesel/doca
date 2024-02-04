package ru.otdel.doca.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate created;
    @LastModifiedDate
    private LocalDate updated;
    private Boolean isDeleted;

    @PrePersist
    public void setDateCreate(){
        created = LocalDate.now();
        isDeleted = false;
    }

    @PreUpdate
    public void setDateUpdate(){
        updated = LocalDate.now();
    }
}
