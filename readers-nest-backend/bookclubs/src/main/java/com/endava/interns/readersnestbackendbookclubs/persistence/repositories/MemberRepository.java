package com.endava.interns.readersnestbackendbookclubs.persistence.repositories;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {

    Optional<Member> findMemberByMemberIdAndBookClub_BookClubId(String memberId, Long bookClubId);

}
