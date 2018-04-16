package com.endava.interns.readersnestbackendusers.persistence.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Accessors(chain = true)
@Document(collection = "users")
public class User {

    private String id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;

    private String password;

    private List<String> bookHistory;
    private String currentBookId;
}
