package com.endava.interns.readersnestbackendusers.services;

import com.endava.interns.readersnestbackendusers.exceptions.AddingUserException;
import com.endava.interns.readersnestbackendusers.exceptions.AuthException;
import com.endava.interns.readersnestbackendusers.exceptions.UserNotFoundException;
import com.endava.interns.readersnestbackendusers.persistence.model.TokenHolder;
import com.endava.interns.readersnestbackendusers.persistence.model.User;
import com.endava.interns.readersnestbackendusers.persistence.repositories.UserRepository;
import com.endava.interns.readersnestbackendusers.security.JwtTokenProvider;
import com.endava.interns.readersnestbackendusers.security.PasswordService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private PasswordService passwordService;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    @Before
    public void setUp(){

        passwordService = mock(PasswordService.class);
        userRepository = mock(UserRepository.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);

        userService = new UserServiceImpl(passwordService, userRepository, jwtTokenProvider);
    }

    @Test
    public void testLoginNonExistingUser(){

        try {
            userService.login("email", "password", 0);
            fail("A user not found exception should have been thrown");
        } catch (UserNotFoundException e) {
            assertEquals("noUser", e.getError());
        } catch (AuthException e) {
            fail("This exception should't have been reached");
        }
    }

    @Test
    public void testLoginWrongCredentials(){

        User user = mock(User.class);
        when(user.getPassword()).thenReturn("someOtherPassword");
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        when(passwordService.checkPassword(anyString(), anyString())).thenReturn(false);

        try {
            userService.login("email", "password", 0);
            fail("An auth exception should have been thrown");
        } catch (UserNotFoundException e) {
            fail("This exception should't have been reached");
        } catch (AuthException e) {
            assertEquals("authLogin", e.getError());
        }
    }

    @Test
    public void testLoginCorrectCredentials(){

        User user = mock(User.class);
        when(user.getPassword()).thenReturn("hashedPassword");
        when(user.getId()).thenReturn("1");
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        when(passwordService.checkPassword(anyString(), anyString())).thenReturn(true);

        when(jwtTokenProvider.createToken("1", 0)).thenReturn("token");
        when(jwtTokenProvider.createRefreshToken("1", 0)).thenReturn("refreshToken");

        try {
            TokenHolder tokenHolder = userService.login("email", "password", 0);
            assertNotNull("The token holder should't be null", tokenHolder);
            assertEquals("1", tokenHolder.getUserId());
            assertEquals("token", tokenHolder.getToken());
            assertEquals("refreshToken", tokenHolder.getRefreshToken());
        } catch (UserNotFoundException | AuthException e) {
            fail("This exception should't have been reached");
        }
    }

    @Test
    public void testFailedSignup(){

        User existingUser = mock(User.class);
        when(userRepository.findByEmail("email")).thenReturn(existingUser);

        User user = new User().setEmail("email");

        try {
            userService.signup(user, 0);
        } catch (AddingUserException e) {
            assertEquals("noCreation", e.getError());
        }
    }

    @Test
    public void testSuccessfulSignup(){

        User newUser = mock(User.class);
        when(newUser.getId()).thenReturn("1");
        when(newUser.getPassword()).thenReturn("someOtherPassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        when(jwtTokenProvider.createToken("1", 0)).thenReturn("token");
        when(jwtTokenProvider.createRefreshToken("1", 0)).thenReturn("refreshToken");

        when(passwordService.createSecurePassword("password")).thenReturn("hashedPassword");

        User user = new User().setEmail("email");
        user.setPassword("password");

        try {
            TokenHolder tokenHolder = userService.signup(user, 0);
            assertNotNull("Token holder shouldn't be null", tokenHolder);
            assertEquals("1", tokenHolder.getUserId());
            assertEquals("token", tokenHolder.getToken());
            assertEquals("refreshToken", tokenHolder.getRefreshToken());
        } catch (AddingUserException e) {
            fail("There shouldn't be any exceptions");
        }
    }

    @Test
    public void testNonExistingUserById(){

        when(userRepository.findById("1")).thenReturn(Optional.empty());

        try {
            userService.findById("1");
            fail("User shouldn't exist");
        } catch (UserNotFoundException e) {
            assertEquals("notFound", e.getError());
        }
    }

    @Test
    public void testExistingUserById(){

        when(userRepository.findById("1")).thenReturn(Optional.of(new User()));

        try {
            userService.findById("1");
        } catch (UserNotFoundException e) {
            fail("User should exist");
        }
    }

    @Test
    public void testNonExistingUserByEmail(){

        when(userRepository.findByEmail("email")).thenReturn(null);

        Optional<User> user = userService.findByEmail("email");
        if (user.isPresent()) fail("There shouldn't be an user with that mail");
    }

    @Test
    public void testExistingUserByEmail(){

        when(userRepository.findByEmail("email")).thenReturn(new User());

        Optional<User> user = userService.findByEmail("email");
        if (!user.isPresent()) fail("There should be an user with that mail");
    }

    @Test
    public void updateNonExistingUser(){
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        try {
            userService.updateUser("1", new User());
            fail("The user shouldn't be updated");
        } catch (UserNotFoundException e) {
            assertEquals("noUser", e.getError());
        }
    }

    @Test
    public void updateExistingUser(){
        User oldUser = new User().setId("1")
                .setUsername("originalUsername");

        when(userRepository.findById("1")).thenReturn(Optional.of(oldUser));

        try {
            User changedUser = new User().setUsername("newUsername");
            userService.updateUser("1", changedUser);
        } catch (UserNotFoundException e) {
            assertEquals("noUser", e.getError());
        }
    }

    @Test
    public void addBookToNonExistingUser(){
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        try {
            userService.updateUser("1", new User());
            fail("The book shouldn't be added");
        } catch (UserNotFoundException e) {
            assertEquals("noUser", e.getError());
        }
    }

    @Test
    public void addBookToExistingUser(){

        User oldUser = new User().setId("1")
                .setUsername("originalUsername")
                .setBookHistory(new ArrayList<>());

        when(userRepository.findById("1")).thenReturn(Optional.of(oldUser));

        try {
            String addedBookId = userService.addBookToHistory("1", "1");
            assertEquals("1", addedBookId);
        } catch (UserNotFoundException e) {
            fail("There shouldn't been any exceptions");
        }
    }

    @Test
    public void testDeleteNonExistingUser(){

        when(userRepository.existsById("1")).thenReturn(false);

        try {
            userService.deleteUser("1");
            fail("The user doesn't exists");
        } catch (UserNotFoundException e) {
            assertEquals("notFound", e.getError());
        }
    }

    @Test
    public void testDeleteExistingUser(){

        when(userRepository.existsById("1")).thenReturn(true);

        try {
            String deletedId = userService.deleteUser("1");
            assertEquals("1", deletedId);
        } catch (UserNotFoundException e) {
            fail("No exception should have been thrown");
        }
    }

    @Test
    public void testFindAll(){
        when(userRepository.findAll()).thenReturn(new ArrayList<>());


        List<User> result = userService.findAll();
        assertEquals(0, result.size());

        List<User> nonEmptyList = new ArrayList<>();
        nonEmptyList.add(new User());
        nonEmptyList.add(new User());

        when(userRepository.findAll()).thenReturn(nonEmptyList);
        result = userService.findAll();
        assertEquals(2, result.size());

    }
}