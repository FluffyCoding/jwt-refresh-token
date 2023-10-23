package com.unity.jwtrefresh.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ServiceUser")
@Table(name = "service_user")
public class ServiceUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 6133392137696091105L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;


    private String loginId;

    @NotNull
    @Email
    private String email;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "token_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "service_user_refresh_token_fk")
    )
    private UserRefreshToken userRefreshToken;


    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

//    private char[] passwords;

    private LocalDate lastLoginDate;

    @CreationTimestamp
    private LocalDate joinDate;

    private String role; //ROLE_USER{ read, edit }, ROLE_ADMIN {delete}

    private String[] authorities;

    private boolean isActive;

    private boolean isLocked;

    private boolean isDeleted;
}
