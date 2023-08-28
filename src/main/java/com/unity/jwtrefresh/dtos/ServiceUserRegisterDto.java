package com.unity.jwtrefresh.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link com.unity.jwtrefresh.entities.ServiceUser} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceUserRegisterDto implements Serializable {
    private String loginId;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;
    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;
    private String password;
}