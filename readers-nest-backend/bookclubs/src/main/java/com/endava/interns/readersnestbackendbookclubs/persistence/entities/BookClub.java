package com.endava.interns.readersnestbackendbookclubs.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "bookclubs")
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bookClubId;
    private String name;
    private String description;
    private String actualBookId;
    private Boolean isPrivate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookClub", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookClub", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Member> members = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookClub", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Administrator> admins = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookClub bookClub = (BookClub) o;
        return Objects.equals(bookClubId, bookClub.bookClubId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(bookClubId);
    }
}