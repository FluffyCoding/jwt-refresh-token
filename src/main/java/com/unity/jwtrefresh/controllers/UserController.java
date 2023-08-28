package com.unity.jwtrefresh.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/user/")
public class UserController {


    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok("I AM Authenticated User");
    }

    @PostMapping
    public ResponseEntity<String> saveUser() {
        return ResponseEntity.ok("Authenticated User Saved");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        return ResponseEntity.ok("Authenticated User Is Deleted");
    }


}
