package com.endava.interns.readersnestbackendusers.security;

import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.Assert.*;

public class PasswordServiceImplTest {

    private PasswordServiceImpl passwordService;

    @Before
    public void setUp() throws Exception {

        passwordService = new PasswordServiceImpl();
    }

    @Test
    public void testCorrectPasswordGeneration(){

        String password = "password";

        String hashedPassword = passwordService.createSecurePassword(password);

        assertTrue("Passwords should match",BCrypt.checkpw(password, hashedPassword));
    }

    @Test
    public void testCorrectPasswordVerification(){

        String password = "password";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        boolean isCorrect = passwordService.checkPassword(password, hashedPassword);

        assertTrue("Password should be correct", isCorrect);
    }

    @Test
    public void testIncorrectPasswordVerification(){

        String password = "password";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        boolean isCorrect = passwordService.checkPassword("incorrect" + password, hashedPassword);

        assertFalse("Password should be correct", isCorrect);

    }
}