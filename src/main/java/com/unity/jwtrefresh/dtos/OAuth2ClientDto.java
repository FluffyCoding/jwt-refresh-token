package com.unity.jwtrefresh.dtos;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2ClientDto {

    private String id;
    private String name;
    private String email;
    private Set<String> authorities = new HashSet<>();

}
