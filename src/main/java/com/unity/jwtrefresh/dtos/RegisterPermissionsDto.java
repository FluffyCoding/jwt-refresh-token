package com.unity.jwtrefresh.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RegisterPermissionsDto {

    private String rollId;
    private List<AuthorizedModule> authorizedModules = new ArrayList<>();

}



