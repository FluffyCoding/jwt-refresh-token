package com.unity.jwtrefresh.controllers;

import com.unity.jwtrefresh.dtos.ServiceUserResponseDto;
import com.unity.jwtrefresh.exceptions.RestExceptionHandling;
import com.unity.jwtrefresh.services.ServiceUserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/home")
public class HomeController extends RestExceptionHandling {

    private final ServiceUserAuthService serviceUserAuthService;

    public HomeController(ServiceUserAuthService serviceUserAuthService) {
        this.serviceUserAuthService = serviceUserAuthService;
    }


    @GetMapping(path = "user")
    public String userHome() {
        return "I am an Authenticated User";
    }

    @GetMapping(path = "/find")
    public ResponseEntity<ServiceUserResponseDto> getServiceUser(@RequestParam Long id) {
        var byId = serviceUserAuthService.findUserById(id);
        return ResponseEntity.ok(byId);
    }
}
