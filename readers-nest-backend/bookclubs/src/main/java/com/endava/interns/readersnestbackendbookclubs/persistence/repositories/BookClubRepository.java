package com.endava.interns.readersnestbackendbookclubs.persistence.repositories;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;
import org.springframework.data.repository.CrudRepository;

public interface BookClubRepository extends CrudRepository<BookClub, Long> {
    //Interface with a lot of methods to persist in DataBase
    Iterable<BookClub> findBookClubByIsPrivateFalse();
}
