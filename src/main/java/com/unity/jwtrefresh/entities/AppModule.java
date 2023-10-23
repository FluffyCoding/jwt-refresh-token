package com.unity.jwtrefresh.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_modules")
public class AppModule {
    @Id
    @Size(max = 50)
    @Column(name = "id", nullable = false, length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Size(max = 45)
    @NotNull
    @Column(name = "module_name", nullable = false, length = 45)
    private String moduleName;

    @NotNull
    @Column(name = "valid", nullable = false)
    private Boolean valid = true;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;


    public AppModule(String id) {
        this.id = id;
    }
}