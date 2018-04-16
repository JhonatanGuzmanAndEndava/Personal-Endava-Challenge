package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.exceptions.NotFoundException;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Administrator;
import org.springframework.http.ResponseEntity;

public interface AdministratorService {

    ResponseEntity<Iterable<Administrator>> getAdminsFromBookClub(Long bookClubId);
    ResponseEntity<Administrator> addAdminToBookClub(Long bookClubId, Administrator admin, String adminId);
    ResponseEntity<Void> deleteAdminFromBookClub(Long bookClubId, String adminId, String otherAdminId);

}
