package com.herotech.app.controller;

import com.herotech.data.dtos.requests.RegistrationRequest;
import com.herotech.data.dtos.responses.RegistrationResponse;
import com.herotech.data.dtos.responses.UserResponse;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/exchange/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) throws HeroXchangeException {
        return new ResponseEntity<>(userService.register(registrationRequest), HttpStatus.CREATED);
    }

    @GetMapping("/details/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) throws HeroXchangeException {
        return ResponseEntity.ok(userService.getUser(userId));
    }

}