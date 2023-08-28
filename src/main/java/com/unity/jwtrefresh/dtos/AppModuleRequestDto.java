package com.unity.jwtrefresh.dtos;

import com.unity.jwtrefresh.entities.AppModule;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link AppModule}
 */

public record AppModuleRequestDto(@NotEmpty @Size(min = 6, max = 35) String moduleName,
                                  @NotNull Boolean valid,
                                  @NotNull Boolean deleted) implements Serializable {
}