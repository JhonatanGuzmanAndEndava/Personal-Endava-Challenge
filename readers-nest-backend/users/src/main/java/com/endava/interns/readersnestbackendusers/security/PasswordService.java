package com.endava.interns.readersnestbackendusers.security;

public interface PasswordService {

    String createSecurePassword(String password);
    boolean checkPassword(String userPassword, String hashedPassword);

}
