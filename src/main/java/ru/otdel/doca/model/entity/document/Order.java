package ru.otdel.doca.model.entity.document;

import jakarta.persistence.*;
import lombok.Data;
import ru.otdel.doca.model.entity.BaseEntity;
import ru.otdel.doca.model.entity.user.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order extends BaseEntity {
    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity customer;

    @OneToOne
    @JoinColumn(nullable = false)
    private UserEntity executor;

    @OneToOne
    @JoinColumn(nullable = false)
    private Card card;

    @Column
    private LocalDateTime executeTo;

    @Column
    private Boolean executed;

}
