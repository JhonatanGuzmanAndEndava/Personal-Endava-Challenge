package com.endava.interns.readersnestbackendbookclubs.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "bookclubs")
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookClubId;
    private String name;
    private String description;
    private String actualBookId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookClub", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    //private List<String> idAdmins;
    //private List<String> idMembers;
    //@OneToMany
    //private List<String> messages;

}