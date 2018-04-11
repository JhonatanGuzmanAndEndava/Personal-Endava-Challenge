package com.endava.interns.readersnestbackendbookclubs.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long memberKey;

    @ManyToOne
    @JoinColumn(name = "book_club_id")
    @JsonIgnore
    private BookClub bookClub;

    private String memberId;

}
