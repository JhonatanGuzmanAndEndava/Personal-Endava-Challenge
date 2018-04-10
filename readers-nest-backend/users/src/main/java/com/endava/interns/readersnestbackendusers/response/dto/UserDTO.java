package com.endava.interns.readersnestbackendusers.response.dto;

import com.endava.interns.readersnestbackendusers.persistence.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<String> bookHistory;
    private String currentBookId;

    public  User dtoToEntity(){
        User user = new User().setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUsername(username)
                .setEmail(email)
                .setCurrentBookId(currentBookId);
        if(bookHistory == null){
            user.setBookHistory(new ArrayList<>());
        }else{
            user.setBookHistory(bookHistory);
        }

        return user;
    }

    public static UserDTO entityToDTO(User user){
        UserDTO dto = new UserDTO().setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setBookHistory(user.getBookHistory())
                .setCurrentBookId(user.getCurrentBookId());
        return dto;
    }
}
