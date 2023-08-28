package com.unity.jwtrefresh.dtos;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.unity.jwtrefresh.entities.AppRole}
 */
@Data
public class AppRoleResponseDto implements Serializable {
    private String id;
    private String roleName;
    private Boolean valid;
    private Boolean deleted;
}