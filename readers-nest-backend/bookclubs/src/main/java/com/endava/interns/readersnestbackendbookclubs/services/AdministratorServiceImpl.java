package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.exceptions.CustomException;
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
        BookClub bk;
        try {
            bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            return bk.getAdmins();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Administrator addAdminToBookClub(Long bookClubId, Administrator admin, String adminId) {

        BookClub bk;
        try {
            bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            administratorRepository.findAdministratorByAdminIdAndBookClub_BookClubId(adminId, bookClubId)
                    .orElseThrow(() -> new NotMatchException("Not match between Admin and Bookclub", "User id is not admin from this Bookclub"));

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

        } catch (CustomException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteAdminFromBookClub(Long bookClubId, String adminId, String otherAdminId) {
        BookClub bk;
        Administrator admin;

        try {
            administratorRepository.findAdministratorByAdminIdAndBookClub_BookClubId(otherAdminId, bookClubId)
                    .orElseThrow(() -> new NotMatchException("Not match between Admin and Bookclub", "User id is not admin from this Bookclub"));

            bk = bookClubRepository.findById(bookClubId).orElseThrow(
                    () -> new NotFoundException("BookClub not found", "BookClub doesn\'t exist in database"));
            admin = administratorRepository.findAdministratorByAdminIdAndBookClub_BookClubId(adminId, bookClubId).orElseThrow(
                    () -> new NotFoundException("Admin not found", "Admin doesn\'t exist in database for this BookClub"));

            bk.getAdmins().remove(admin);
            bk.setAdmins(bk.getAdmins());

            bookClubRepository.save(bk);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }
}
