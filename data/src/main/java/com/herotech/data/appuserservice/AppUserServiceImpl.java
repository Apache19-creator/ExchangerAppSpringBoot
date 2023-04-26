package com.herotech.data.appuserservice;

import com.herotech.data.repositories.UserRepository;
import com.herotech.data.dtos.responses.UserResponse;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.entities.AppUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public AppUser findUserByEmail(String email) throws HeroXchangeException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new HeroXchangeException("invalid email or password")
        );
    }

    @Override
    public void saveUser(AppUser user) {
        userRepository.save(user);
    }

    @Override
    public AppUser findUserByPasswordResetToken(String passwordResetToken) throws HeroXchangeException {
        return userRepository.findByPasswordResetToken(passwordResetToken)
                .orElseThrow(() -> new HeroXchangeException("invalid or expired token"));

    }

    @Override
    public UserResponse getUser(Long userId) throws HeroXchangeException {
           AppUser user = userRepository.findById(userId).orElseThrow(
                () -> new HeroXchangeException("user not found"));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public AppUser findUserByEmailVerificationToken(String token) throws HeroXchangeException {
           return userRepository.findByEmailVerificationToken(token).orElseThrow(
                () -> new HeroXchangeException("invalid token")
        );
    }
}
