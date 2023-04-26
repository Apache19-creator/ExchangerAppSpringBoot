package com.herotech.data.utils;


import com.herotech.data.exceptions.HeroXchangeException;

public class Validator {
       public static void ensureValidPassword(String password) throws HeroXchangeException {
        if (password.length() > 16 || !password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~@$!%^(#){/}`<>*,.?&])[A-Za-z\\d@~$!`%<>.^,(#){/}*?&]{8,}")) {
            throw new HeroXchangeException("""
                    invalid password, password must contain at least 8 and maximum of 16 characters, a number ,
                     an Uppercase and a  special character
                    """);
        }
    }


    public static void ensureValidEmail(String email) throws HeroXchangeException {
        if (!isValidEmail(email)) {
            throw new HeroXchangeException("invalid email ");
        }
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }

    public static void ensureBothPasswordMatches(String password, String confirmPassword) throws HeroXchangeException {
        if (!password.equals(confirmPassword)) throw new HeroXchangeException("passwords don't match");
    }

    public static void ensureValidPhone(String phoneNumber) throws HeroXchangeException {
        if (!phoneNumber.matches("^(?!\\b(0)\\1+\\b)(\\+?\\d{1,3}[. -]?)?\\(?\\d{3}\\)?([. -]?)\\d{3}\\3\\d{4}$")) {
            throw new HeroXchangeException("invalid phone number");
        }
    }
}
