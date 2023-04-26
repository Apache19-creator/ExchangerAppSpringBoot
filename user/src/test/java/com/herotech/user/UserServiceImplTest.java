package com.herotech.user;

import com.herotech.data.dtos.requests.RegistrationRequest;
import com.herotech.data.dtos.responses.RegistrationResponse;
import com.herotech.data.entities.VerificationToken;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.repositories.UserRepository;
import com.herotech.notification.EmailService;
import com.herotech.shared.services.verificationtoken.VerificationTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = UserServiceImpl.class)
@ExtendWith({SpringExtension.class})
class UserServiceImplTest {
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private VerificationTokenService verificationTokenService;
    @MockBean
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Test
    void register() throws HeroXchangeException {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(verificationTokenService.generateEMailVerificationToken(any())).thenReturn(new VerificationToken("token", LocalDateTime.now().plusHours(3), "mike@semicolon,africa"));
        when(passwordEncoder.encode(any())).thenReturn("encodedString");

        RegistrationResponse registrationResponse = userService.register(new RegistrationRequest("Michael", "Boyo", "mike@semicolon.africa",
                "1234", "09039484930", "P@$$word123", "P@$$word123"));

        assertEquals(registrationResponse.getFirstName(), "Michael");
        assertEquals(registrationResponse.getEmail(), "mike@semicolon.africa");
        assertEquals(registrationResponse.getLastName(), "Boyo");

        verify(userRepository).existsByEmail(any());
        verify(userRepository).save(any());
        verify(verificationTokenService).generateEMailVerificationToken(any());
        verify(emailService).sendRegistrationEmail(any());
    }

    @Test
    void getUser() {
    }
}