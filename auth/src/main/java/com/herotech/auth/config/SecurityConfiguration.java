package com.herotech.auth.config;

import com.herotech.auth.services.jwt.JwtAuthenticationFilter;
import com.herotech.auth.utils.ExceptionHandler;
import com.herotech.auth.utils.UnAuthorizedEntryPoint;
import com.herotech.data.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UnAuthorizedEntryPoint unAuthorizedEntryPoint;
    private final String[] NO_AUTH_ROUTES = {
            "/api/exchange/v1/user/register", "/api/exchange/v1/auth/login",
            "/api/exchange/v1/auth/forgot-password/**", "/api/exchange/v1/auth/reset-password",
            "/api/exchange/v1/auth/verify-email/**",

            // currency for homepage
            "/api/exchange/v1/currency/**",

            // resend emails
            "/api/exchange/v1/auth/resend-email-otp-verification-email/**",
            "/api/exchange/v1/auth/resend-forgot-password-email/**",

            // swagger ui docs
          "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(NO_AUTH_ROUTES)
                .permitAll()
                .requestMatchers("/api/exchange/v1/super-admin/**").hasAnyAuthority(Role.SUPER_ADMIN.name())
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unAuthorizedEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilterBean(), JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public ExceptionHandler exceptionHandlerFilterBean() {
        return new ExceptionHandler();
    }
}