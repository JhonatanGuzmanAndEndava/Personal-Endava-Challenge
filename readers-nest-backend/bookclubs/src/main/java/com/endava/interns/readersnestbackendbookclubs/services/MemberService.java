package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.exceptions.DuplicatedException;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Member;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    ResponseEntity<Iterable<Member>> getMembersFromBookClub(Long bookClubId);
    ResponseEntity<Member> addMemberToBookClub(Long bookClubId, Member member, String adminId) throws DuplicatedException;
    ResponseEntity<Void> deleteMemberFromBookClub(Long bookClubId, String memberId, String adminId);

}
