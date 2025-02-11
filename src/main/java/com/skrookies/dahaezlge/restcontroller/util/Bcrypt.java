package com.skrookies.dahaezlge.restcontroller.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Bcrypt {

    public String hashPassword(String user_pw) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user_pw);

        return encodedPassword;
    }
}
