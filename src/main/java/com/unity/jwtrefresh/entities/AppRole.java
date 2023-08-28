package com.unity.jwtrefresh.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE app_roles SET deleted = true , valid=false WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted <> true")
@Table(name = "app_roles", schema = "authjwt", indexes = {
        @Index(name = "role_name_UNIQUE", columnList = "role_name", unique = true)
})
public class AppRole {
    @Id
    @Column(name = "id", nullable = false, length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @NotNull
    @Column(name = "role_name", nullable = false, length = 45)
    private String roleName;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "deleted")
    private Boolean deleted;

}