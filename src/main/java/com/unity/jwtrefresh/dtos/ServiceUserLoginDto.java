package com.unity.jwtrefresh.dtos;

import java.io.Serializable;

/**
 * A DTO for the {@link com.unity.jwtrefresh.entities.ServiceUser} entity
 */
public record ServiceUserLoginDto(String loginId, String password) implements Serializable {
}