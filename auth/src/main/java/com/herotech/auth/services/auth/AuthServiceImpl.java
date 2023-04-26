package com.herotech.auth.services.auth;

import com.herotech.auth.services.jwt.JwtService;
import com.herotech.data.repositories.UserRepository;
import com.herotech.data.entities.SecureUser;
import com.herotech.data.dtos.requests.LoginRequest;
import com.herotech.data.dtos.responses.LoginResponse;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.entities.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;


    @Override
    public LoginResponse login(LoginRequest request) throws HeroXchangeException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new HeroXchangeException("invalid email or password");
        }

        AppUser foundUser = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new HeroXchangeException("invalid email or password")
        );
        SecureUser user = new SecureUser(foundUser);
        String jwtToken = jwtService.generateToken(user);
        return LoginResponse.of(jwtToken, foundUser.getId(), foundUser.getKycStatus());
    }

    @Override
    public String logout(String email) {
        return "Logout Successful";
    }

}
