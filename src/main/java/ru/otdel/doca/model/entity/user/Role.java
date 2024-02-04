package ru.otdel.doca.model.entity.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.otdel.doca.model.entity.BaseEntity;

import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity {
    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<UserEntity> userEntities;
}
