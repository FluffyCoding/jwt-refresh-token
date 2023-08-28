package com.unity.jwtrefresh.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * DTO for {@link com.unity.jwtrefresh.entities.AppRole}
 */
public record AppRoleRequestDto(@Size(max = 45) @NotBlank @Length(min = 6, max = 35) String roleName,
                                Boolean valid,
                                Boolean deleted) implements Serializable {
}