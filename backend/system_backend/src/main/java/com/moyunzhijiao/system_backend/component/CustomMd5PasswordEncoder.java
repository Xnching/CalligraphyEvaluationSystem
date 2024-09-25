package com.moyunzhijiao.system_backend.component;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.Arrays;

/*
* 加密器
* */
public class CustomMd5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        // Return the raw password as a string
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // Directly compare the raw password with the encoded password
        return rawPassword.toString().equals(encodedPassword);
    }

}
