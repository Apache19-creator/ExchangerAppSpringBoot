package com.herotech.app.controller;


import com.herotech.auth.services.auth.AuthService;
import com.herotech.data.dtos.requests.LoginRequest;
import com.herotech.data.dtos.responses.LoginResponse;
import com.herotech.data.exceptions.HeroXchangeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/exchange/v1/auth")
@RequiredArgsConstructor
public class AuthController {
      private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws HeroXchangeException {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout/{email}")
    public ResponseEntity<String> logout(@PathVariable String email) throws HeroXchangeException {
        return ResponseEntity.ok(authService.logout(email));
    }
}
