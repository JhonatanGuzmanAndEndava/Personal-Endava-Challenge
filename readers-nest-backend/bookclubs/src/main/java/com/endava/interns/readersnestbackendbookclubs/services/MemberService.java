package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Member;

public interface MemberService {

    Iterable<Member> getMembersFromBookClub(Long bookClubId);
    Member addMemberToBookClub(Long bookClubId, Member member);
    void deleteMemberFromBookClub(Long bookClubId, String memberId);

}
