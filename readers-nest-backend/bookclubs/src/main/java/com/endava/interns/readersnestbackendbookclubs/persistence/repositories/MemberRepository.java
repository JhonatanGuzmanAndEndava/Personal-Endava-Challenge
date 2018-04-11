package com.endava.interns.readersnestbackendbookclubs.persistence.repositories;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {

}
