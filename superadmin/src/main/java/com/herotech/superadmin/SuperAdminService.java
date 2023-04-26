package com.herotech.superadmin;

import com.herotech.data.exceptions.HeroXchangeException;

public interface SuperAdminService {
    String inviteAdmin(String email) throws HeroXchangeException;
}
