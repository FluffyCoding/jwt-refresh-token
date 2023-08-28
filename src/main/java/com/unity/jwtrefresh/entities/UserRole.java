package com.unity.jwtrefresh.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_roles", schema = "authjwt", indexes = {
        @Index(name = "fk_user_roles_unity_user1_idx", columnList = "unity_user_id"),
        @Index(name = "fk_user_roles_app_roles1_idx", columnList = "app_roles_id")
})
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @MapsId("appRolesId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_roles_id", nullable = false)
    private AppRole appRoles;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unity_user_id", nullable = false)
    private ServiceUser unityUser;

}