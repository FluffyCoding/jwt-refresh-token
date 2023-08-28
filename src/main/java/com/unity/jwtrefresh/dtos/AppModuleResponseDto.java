package com.unity.jwtrefresh.dtos;

import com.unity.jwtrefresh.entities.AppModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link AppModule}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppModuleResponseDto implements Serializable {

    String id;
    String moduleName;
    Boolean valid;
    Boolean deleted;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppModuleResponseDto that = (AppModuleResponseDto) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(moduleName, that.moduleName)) return false;
        if (!Objects.equals(valid, that.valid)) return false;
        return Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (moduleName != null ? moduleName.hashCode() : 0);
        result = 31 * result + (valid != null ? valid.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }
}