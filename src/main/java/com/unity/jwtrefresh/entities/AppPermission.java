package com.unity.jwtrefresh.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_permissions", schema = "authjwt", indexes = {
        @Index(name = "auth_name_UNIQUE", columnList = "permission_name", unique = true)
})
public class AppPermission {
    @Id
    @Size(max = 50)
    @Column(name = "id", nullable = false, length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Size(max = 45)
    @Column(name = "permission_name", length = 45)
    private String permissionName;

    public AppPermission(String id) {
        this.id = id;
    }
}