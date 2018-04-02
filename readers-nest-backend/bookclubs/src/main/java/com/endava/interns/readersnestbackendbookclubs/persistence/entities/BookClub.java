package com.endava.interns.readersnestbackendbookclubs.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookClubId;
    private String name;
    private String description;
    private String actualBookId;
    //private List<String> idAdmins;
    //private List<String> idMembers;
    //@OneToMany
    //private List<String> messages;

}