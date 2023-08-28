package com.unity.jwtrefresh.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AuthorizedModule {

    private String moduleId;
    private List<Permission> permissions;
}
