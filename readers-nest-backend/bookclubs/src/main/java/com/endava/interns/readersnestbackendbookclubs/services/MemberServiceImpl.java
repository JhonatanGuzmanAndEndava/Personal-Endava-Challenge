package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Administrator;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.BookClub;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Member;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.AdministratorRepository;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.BookClubRepository;
import com.endava.interns.readersnestbackendbookclubs.persistence.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private BookClubRepository bookClubRepository;
    private AdministratorRepository administratorRepository;
    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(BookClubRepository bookClubRepository, AdministratorRepository administratorRepository, MemberRepository memberRepository) {
        this.bookClubRepository = bookClubRepository;
        this.administratorRepository = administratorRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Iterable<Member> getMembersFromBookClub(Long bookClubId) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        return bk.getMembers();
    }

    @Override
    public Member addMemberToBookClub(Long bookClubId, Member member) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));

        if(bk.getMembers().contains(member))
            throw new NullPointerException("User already is a member");

        member.setBookClub(bk);
        bk.getMembers().add(member);

        memberRepository.save(member);
        bookClubRepository.save(bk);
        return member;
    }

    @Override
    public void deleteMemberFromBookClub(Long bookClubId, String memberId) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        Member member = memberRepository.findMemberByMemberIdAndBookClub_BookClubId(memberId,bookClubId)
                .orElseThrow(() -> new NullPointerException("Member is not from this Bookclub"));

        List<Member> members = bk.getMembers();
        if(members.remove(member))
            bk.setMembers(members);

        Optional<Administrator> admin = administratorRepository.findAdministratorByAdminIdAndBookClub_BookClubId(memberId, bookClubId);
        if(admin.isPresent()) {
            List<Administrator> admins = bk.getAdmins();
            admins.remove(admin.get());
            bk.setAdmins(admins);
        }
        bookClubRepository.save(bk);
    }
}
