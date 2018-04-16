package com.endava.interns.readersnestbackendusers.controllers;

import com.endava.interns.readersnestbackendusers.exceptions.CustomException;
import com.endava.interns.readersnestbackendusers.persistence.model.TokenHolder;
import com.endava.interns.readersnestbackendusers.response.*;
import com.endava.interns.readersnestbackendusers.persistence.model.User;
import com.endava.interns.readersnestbackendusers.response.dto.CredentialsDTO;
import com.endava.interns.readersnestbackendusers.response.dto.UserDTO;
import com.endava.interns.readersnestbackendusers.response.dto.TokenHolderDTO;
import com.endava.interns.readersnestbackendusers.response.dto.UserWithCredentialsDTO;
import com.endava.interns.readersnestbackendusers.security.AuthService;
import com.endava.interns.readersnestbackendusers.security.SecurityConstants;
import com.endava.interns.readersnestbackendusers.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
public class UsersController {

    private UserService userService;
    private AuthService authService;

    @Autowired
    public UsersController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping(path = "/login")
    public ResponseMessage<TokenHolderDTO>
    signin(@RequestBody CredentialsDTO credentials) throws CustomException {

        TokenHolder token = userService.login(credentials.getEmail(), credentials.getPassword(), System.currentTimeMillis());

        return new ResponseMessage<>(TokenHolderDTO.entityToDTO(token));
    }

    @PostMapping(path = "/signup")
    public ResponseMessage<TokenHolderDTO> signupNewUser(@RequestBody UserWithCredentialsDTO newUser) throws CustomException{

        UserDTO userDTO = newUser.getUser();
        CredentialsDTO credentialsDTO = newUser.getCredentials();

        User user = userDTO.dtoToEntity();
        user.setPassword(credentialsDTO.getPassword());

        TokenHolder createdUserToken = userService.signup(user, System.currentTimeMillis());
        return new ResponseMessage<>(TokenHolderDTO.entityToDTO(createdUserToken));
    }

    @GetMapping(path = "/token/refresh")
    public ResponseMessage<String> getRefreshToken(@RequestParam("token") String refreshToken) throws CustomException{

        String token = authService.refreshToken(refreshToken, System.currentTimeMillis());

        return new ResponseMessage<>(token);
    }

    @GetMapping(path = "/{userId}")
    public ResponseMessage<UserDTO> findUserById(@PathVariable("userId") String id) throws CustomException{

        User user  = userService.findById(id);

        return new ResponseMessage<>(UserDTO.entityToDTO(user));

    }

    @GetMapping
    public ResponseMessage<List<UserDTO>> getAllUsers(){

        List<User> users = userService.findAll();

        List<UserDTO> usersDTOS = users.stream().map(UserDTO::entityToDTO).collect(Collectors.toList());

        return new ResponseMessage<>(usersDTOS);
    }

    @PutMapping(path = "/{userId}")
    public ResponseMessage<UserDTO>
    updateUser(@RequestHeader(value = SecurityConstants.HEADER_STRING, required = false) String authorization,
                                               @PathVariable("userId") String userId, @RequestBody User updatedUser)
    throws CustomException{

        authService.checkJWT(userId, authorization);

        User user = userService.updateUser(userId, updatedUser);

        return new ResponseMessage<>(UserDTO.entityToDTO(user));
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseMessage<String>
    deleteBook(@RequestHeader(value = SecurityConstants.HEADER_STRING, required = false) String authorization,
               @PathVariable("userId") String userId) throws CustomException {

        authService.checkJWT(userId, authorization);

        String deletedUser = userService.deleteUser(userId);
        return new ResponseMessage<>(deletedUser);
    }

    @PutMapping(path = "/{userId}/addBook/{bookId}")
    public ResponseMessage<String>
    addBookToHistory(@RequestHeader(value = SecurityConstants.HEADER_STRING, required = false) String authorization,
                     @PathVariable("userId") String userId, @PathVariable("bookId") String bookId) throws CustomException {

        authService.checkJWT(userId, authorization);

        String addedBook = userService.addBookToHistory(userId, bookId);
        return new ResponseMessage<>(addedBook);

    }
}
