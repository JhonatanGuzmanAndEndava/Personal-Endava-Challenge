package com.endava.interns.readersnestbackendbookclubs.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "administrators")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long administratorKey;

    @ManyToOne
    @JoinColumn(name = "book_club_id")
    @JsonIgnore
    private BookClub bookClub;

    private String adminId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        return Objects.equals(adminId, that.adminId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(adminId);
    }
}
