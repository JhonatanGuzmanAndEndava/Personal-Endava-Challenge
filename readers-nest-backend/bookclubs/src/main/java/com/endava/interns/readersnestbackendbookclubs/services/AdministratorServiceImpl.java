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
public class AdministratorServiceImpl implements  AdministratorService {

    private BookClubRepository bookClubRepository;
    private AdministratorRepository administratorRepository;
    private MemberRepository memberRepository;

    @Autowired
    public AdministratorServiceImpl(BookClubRepository bookClubRepository, AdministratorRepository administratorRepository, MemberRepository memberRepository) {
        this.bookClubRepository = bookClubRepository;
        this.administratorRepository = administratorRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Iterable<Administrator> getAdminsFromBookClub(Long bookClubId) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        return bk.getAdmins();
    }

    @Override
    public Administrator addAdminToBookClub(Long bookClubId, Administrator admin) {

        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        Optional<Member> member = memberRepository.findMemberByMemberIdAndBookClub_BookClubId(admin.getAdminId(), bookClubId);

        if(member.isPresent()) {
            List<Administrator> admins = bk.getAdmins();
            admin.setBookClub(bk);
            admins.add(admin);
            bk.setAdmins(admins);
        }else {
            Member newMember = new Member();
            newMember.setMemberId(admin.getAdminId());
            newMember.setBookClub(bk);
            admin.setBookClub(bk);

            bk.getMembers().add(newMember);
            bk.getAdmins().add(admin);

            memberRepository.save(newMember);
        }

        administratorRepository.save(admin);
        bookClubRepository.save(bk);

        return admin;
    }

    @Override
    public void deleteAdminFromBookClub(Long bookClubId, String adminId) {
        BookClub bk = bookClubRepository.findById(bookClubId).orElseThrow(() -> new NullPointerException("BookClub not found"));
        Administrator admin = administratorRepository.findAdministratorByAdminIdAndBookClub_BookClubId(adminId, bookClubId).orElseThrow(() -> new NullPointerException("Admin not found"));

        bk.getAdmins().remove(admin);
        bk.setAdmins(bk.getAdmins());

        bookClubRepository.save(bk);
    }
}
