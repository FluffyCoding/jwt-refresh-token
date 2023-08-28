package com.unity.jwtrefresh.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "UserRefreshToken")
@Table(name = "user_refresh_token", schema = "authjwt")
@Getter
@Setter
public class UserRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "userRefreshToken")
    private ServiceUser user;

    private String token;

    private LocalDateTime issueDateTime;

    private LocalDateTime expiryDateTime;

    private boolean isValid;

}
