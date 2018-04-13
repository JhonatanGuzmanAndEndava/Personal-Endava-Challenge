package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.exceptions.DuplicatedException;
import com.endava.interns.readersnestbackendbookclubs.exceptions.NotFoundException;
import com.endava.interns.readersnestbackendbookclubs.exceptions.NotMatchException;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Member;

public interface MemberService {

    Iterable<Member> getMembersFromBookClub(Long bookClubId);
    Member addMemberToBookClub(Long bookClubId, Member member) throws DuplicatedException;
    void deleteMemberFromBookClub(Long bookClubId, String memberId);

}
