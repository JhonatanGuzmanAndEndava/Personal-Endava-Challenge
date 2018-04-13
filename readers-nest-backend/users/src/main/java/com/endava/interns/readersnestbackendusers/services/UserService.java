package com.endava.interns.readersnestbackendusers.services;

import com.endava.interns.readersnestbackendusers.exceptions.*;
import com.endava.interns.readersnestbackendusers.persistence.model.TokenHolder;
import com.endava.interns.readersnestbackendusers.persistence.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    TokenHolder login(String email, String password, long loginTime) throws UserNotFoundException, AuthException;
    TokenHolder signup(User newUser, long signupTime) throws AddingUserException;
    User findById(String id) throws UserNotFoundException;
    Optional<User> findByEmail(String email);
    User updateUser(String id, User updatedUser) throws UserNotFoundException, ExternalServiceException;
    String addBookToHistory(String userId, String bookId) throws UserNotFoundException, AlreadyExistsException, ExternalServiceException;
    String deleteUser(String id) throws UserNotFoundException;
    List<User> findAll();
}
