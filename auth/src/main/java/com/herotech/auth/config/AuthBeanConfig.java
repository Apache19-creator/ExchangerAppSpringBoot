package com.herotech.auth.config;

import com.herotech.data.entities.SecureUser;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.entities.AppUser;
import com.herotech.data.appuserservice.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class AuthBeanConfig {
    private final AppUserService userRepository;

    private final PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            try {
                AppUser user = userRepository.findUserByEmail(email);
                if (!user.isVerified()) throw new HeroXchangeException("please verify your email");
                return new SecureUser(user);
            } catch (HeroXchangeException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
