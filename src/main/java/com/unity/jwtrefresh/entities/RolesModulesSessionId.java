package com.unity.jwtrefresh.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class RolesModulesSessionId implements Serializable {

    @Column(name = "app_roles_id")
    private String appRoleId;

    @Column(name = "app_modules_id")
    private String appModuleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesModulesSessionId that = (RolesModulesSessionId) o;

        if (!Objects.equals(appRoleId, that.appRoleId)) return false;
        return Objects.equals(appModuleId, that.appModuleId);
    }

    @Override
    public int hashCode() {
        int result = appRoleId != null ? appRoleId.hashCode() : 0;
        result = 31 * result + (appModuleId != null ? appModuleId.hashCode() : 0);
        return result;
    }
}
