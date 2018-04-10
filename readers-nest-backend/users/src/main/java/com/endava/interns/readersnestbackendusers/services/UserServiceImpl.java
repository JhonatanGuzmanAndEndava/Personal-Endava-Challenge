package com.endava.interns.readersnestbackendusers.services;

import com.endava.interns.readersnestbackendusers.exceptions.AddingUserException;
import com.endava.interns.readersnestbackendusers.exceptions.AuthException;
import com.endava.interns.readersnestbackendusers.exceptions.UserNotFoundException;
import com.endava.interns.readersnestbackendusers.persistence.model.TokenHolder;
import com.endava.interns.readersnestbackendusers.persistence.model.User;
import com.endava.interns.readersnestbackendusers.persistence.repositories.UserRepository;
import com.endava.interns.readersnestbackendusers.security.JwtTokenProvider;
import com.endava.interns.readersnestbackendusers.security.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private PasswordService passwordService;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(PasswordService passwordService, UserRepository userRepository,
                           JwtTokenProvider jwtTokenProvider) {
        this.passwordService = passwordService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public TokenHolder login(String email, String password, long login_time) throws UserNotFoundException, AuthException {

        User user = userRepository.findByEmail(email);

        if(user == null)
            throw new UserNotFoundException("noUser", "There is no user with that email");

        if(!passwordService.checkPassword(password, user.getPassword()))
            throw new AuthException("authLogin", "Can't login with those credentials");

        String id = user.getId();
        return new TokenHolder(id,
                jwtTokenProvider.createToken(id, login_time), jwtTokenProvider.createRefreshToken(id, login_time));
    }

    @Override
    public TokenHolder signup(User user, long signupTime) throws AddingUserException {
        if(userRepository.findByEmail(user.getEmail()) == null){
            String unsecuredPassword = user.getPassword();
            user.setPassword(passwordService.createSecurePassword(unsecuredPassword));
            User newUser = userRepository.save(user);

            String id = newUser.getId();
            return new TokenHolder(id,
                    jwtTokenProvider.createToken(id, signupTime), jwtTokenProvider.createRefreshToken(id, signupTime));
        }else{
            throw new AddingUserException("noCreation",
                    "The user with the specified information couldn't be created");
        }
    }

    @Override
    public User findById(String id) throws UserNotFoundException{
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException("notFound","There is no user with that ID");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public User updateUser(String id, User updatedUser) throws UserNotFoundException {
        Optional<User> oldUserOptional = userRepository.findById(id);

        if(!oldUserOptional.isPresent()) throw new UserNotFoundException("noUser", "You can't update this user");

        User oldUser = oldUserOptional.get();
        return userRepository.save(updateFromNew(oldUser, updatedUser));
    }

    @Override
    public String addBookToHistory(String userId, String bookId) throws UserNotFoundException {

        //TODO check the book exists before adding it to the user

        Optional<User> optionalUser = userRepository.findById(userId);

        if(!optionalUser.isPresent()) throw new UserNotFoundException("notFound","There is no user with that ID");

        User user = optionalUser.get();
        //TODO Validate that the book hasn't been added yet

        List<String> bookHistory = user.getBookHistory();
        bookHistory.add(bookId);
        user.setBookHistory(bookHistory);

        userRepository.save(user);
        return bookId;
    }

    @Override
    public String deleteUser(String id) throws UserNotFoundException{

        if (!userRepository.existsById(id)) throw new UserNotFoundException("notFound","There is no user with that ID");

        userRepository.deleteById(id);

        return id;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    private User updateFromNew(User oldUser, User changedUser){

        if(changedUser.getFirstName() != null) oldUser.setFirstName(changedUser.getFirstName());
        if(changedUser.getLastName() != null) oldUser.setLastName(changedUser.getLastName());
        if(changedUser.getUsername() != null) oldUser.setUsername(changedUser.getUsername());
        if(changedUser.getEmail() != null) oldUser.setEmail(changedUser.getEmail());

        if(changedUser.getBookHistory() != null) oldUser.setBookHistory(changedUser.getBookHistory());
        if(changedUser.getCurrentBookId() != null) oldUser.setCurrentBookId(changedUser.getCurrentBookId());

        return oldUser;
    }
}
