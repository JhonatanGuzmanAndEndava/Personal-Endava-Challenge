package com.endava.interns.readersnestbackendusers.persistence.repositories;

import com.endava.interns.readersnestbackendusers.persistence.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{

    User findByEmail(String email);
}
