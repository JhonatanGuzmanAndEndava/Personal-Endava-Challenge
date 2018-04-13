package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.exceptions.CustomException;
import com.endava.interns.readersnestbackendbookclubs.exceptions.DuplicatedException;
import com.endava.interns.readersnestbackendbookclubs.exceptions.NotFoundException;
import com.endava.interns.readersnestbackendbookclubs.exceptions.NotMatchException;
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
        BookClub bk;
        try {
            bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            return bk.getMembers();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Member addMemberToBookClub(Long bookClubId, Member member, String adminId) throws DuplicatedException {
        BookClub bk;
        try {
            bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            administratorRepository.findAdministratorByAdminIdAndBookClub_BookClubId(adminId, bookClubId)
                    .orElseThrow(() -> new NotMatchException("Not match between Admin and Bookclub", "User id is not admin from this Bookclub"));

            if(bk.getMembers().contains(member))
                throw new DuplicatedException("Duplicated user", "User already is a member");

            member.setBookClub(bk);
            bk.getMembers().add(member);

            memberRepository.save(member);
            bookClubRepository.save(bk);
            return member;

        } catch (CustomException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteMemberFromBookClub(Long bookClubId, String memberId, String adminId) {
        BookClub bk;
        Member member;
        try {
            bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            member = memberRepository.findMemberByMemberIdAndBookClub_BookClubId(memberId,bookClubId)
                    .orElseThrow(() -> new NotMatchException("Not match between Member and Bookclub", "Member is not from this Bookclub"));
            administratorRepository.findAdministratorByAdminIdAndBookClub_BookClubId(adminId, bookClubId)
                    .orElseThrow(() -> new NotMatchException("Not match between Admin and Bookclub", "User id is not admin from this Bookclub"));

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

        } catch (CustomException e) {
            e.printStackTrace();
        }
    }
}
