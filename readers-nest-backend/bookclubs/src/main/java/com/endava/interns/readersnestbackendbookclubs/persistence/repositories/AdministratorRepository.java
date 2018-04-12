package com.endava.interns.readersnestbackendbookclubs.persistence.repositories;

import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Administrator;
import com.endava.interns.readersnestbackendbookclubs.persistence.entities.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdministratorRepository extends CrudRepository<Administrator, Long> {

    Optional<Administrator> findAdministratorByAdminIdAndBookClub_BookClubId(String adminId, Long bookClubId);

}
