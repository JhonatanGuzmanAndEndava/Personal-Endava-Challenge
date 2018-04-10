package com.endava.interns.readersnestbackendusers.response.dto;

import lombok.Data;

@Data
public class UserWithCredentialsDTO {

    private UserDTO user;
    private CredentialsDTO credentials;

}
