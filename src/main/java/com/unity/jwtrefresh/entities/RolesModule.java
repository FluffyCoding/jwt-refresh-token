package com.unity.jwtrefresh.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "RolesModule")
@Table(name = "roles_modules", schema = "authjwt", indexes = {
        @Index(name = "fk_roles_modules_app_roles1_idx", columnList = "app_roles_id"),
        @Index(name = "fk_roles_modules_app_modules1_idx", columnList = "app_modules_id")
})
public class RolesModule {

    @EmbeddedId
    private RolesModulesSessionId id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("appRoleId")
    @JoinColumn(name = "app_roles_id", nullable = false)
    private AppRole appRoles;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("appModuleId")
    @JoinColumn(name = "app_modules_id", nullable = false)
    private AppModule appModules;

}