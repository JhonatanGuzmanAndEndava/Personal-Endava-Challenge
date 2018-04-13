package com.endava.interns.readersnestbackendbookclubs.services;

import com.endava.interns.readersnestbackendbookclubs.exceptions.NotFoundException;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Administrator;

public interface AdministratorService {

    Iterable<Administrator> getAdminsFromBookClub(Long bookClubId);
    Administrator addAdminToBookClub(Long bookClubId, Administrator admin, String adminId);
    void deleteAdminFromBookClub(Long bookClubId, String adminId, String otherAdminId);

}
