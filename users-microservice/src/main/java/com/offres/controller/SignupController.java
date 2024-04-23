package com.offres.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.offres.dto.SignupRequest;
import com.offres.dto.UserDto;
import com.offres.service.user.UserService;


@RestController
public class SignupController {

    @Autowired
    UserService userService;

    //User can register with this API

    @PostMapping({"/sign-up"})
    public ResponseEntity<?> signupUser(@RequestBody(required = true) SignupRequest signupRequest) throws Exception {

        if (userService.hasUserWithEmail(signupRequest.getEmail()))
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);

        UserDto createdUser = userService.createUser(signupRequest);
        if (createdUser == null)
            return new ResponseEntity<>("User not created, come again later", HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }



}
