package ru.otdel.doca.model.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.otdel.doca.model.entity.BaseEntity;
import ru.otdel.doca.model.entity.document.Card;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class UserEntity extends BaseEntity {

    private String login;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "userLogin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Card> cards;

    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthday;


}
