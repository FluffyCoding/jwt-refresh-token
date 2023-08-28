package com.unity.jwtrefresh.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "modules_permissions", schema = "authjwt", indexes = {
        @Index(name = "role_id_UNIQUE", columnList = "role_id", unique = true),
        @Index(name = "fk_modules_permissions_app_modules1_idx", columnList = "app_modules_id"),
        @Index(name = "fk_modules_permissions_app_permissions1_idx", columnList = "app_permissions_id")
})
public class ModulesPermission {
    @Id
    @Size(max = 50)
    @Column(name = "id", nullable = false, length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_modules_id", nullable = false)
    private AppModule appModules;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_permissions_id", nullable = false)
    private AppPermission appPermissions;

    @Size(max = 50)
    @NotNull
    @Column(name = "role_id", nullable = false, length = 50)
    private String roleId;

    public ModulesPermission(AppModule appModules, AppPermission appPermissions, String roleId) {
        this.appModules = appModules;
        this.appPermissions = appPermissions;
        this.roleId = roleId;
    }
}