package com.endava.interns.readersnestbackendbooks.persistence.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Data
@Entity
//@Table(name = "bookclubs")
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookClubId;
    private String name;
    private String description;
    private String actualBookId;
    private List<String> idAdmins;
    private List<String> idMembers;
    private List<String> messages;

}
