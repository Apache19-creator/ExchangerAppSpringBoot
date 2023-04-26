package com.herotech.superadmin;

import com.herotech.data.appuserservice.AppUserService;
import com.herotech.data.entities.AppUser;
import com.herotech.data.enums.Role;
import com.herotech.data.exceptions.HeroXchangeException;
import com.herotech.data.utils.Validator;
import com.herotech.notification.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SuperAdminServiceImpl implements SuperAdminService {
    private final EmailService emailService;
    private final AppUserService userService;

    @Override
    public String inviteAdmin(String email) throws HeroXchangeException {
        Validator.ensureValidEmail(email);
        AppUser appUser = new AppUser(email);
        appUser.setRole(Role.ADMIN);
        userService.saveUser(appUser);
        emailService.sendAdminInviteEmail(email);
        return "email invite sent to admin";
    }
}