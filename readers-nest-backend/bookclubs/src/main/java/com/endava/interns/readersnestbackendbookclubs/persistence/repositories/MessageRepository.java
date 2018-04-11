package com.endava.interns.readersnestbackendbookclubs.persistence.repositories;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
