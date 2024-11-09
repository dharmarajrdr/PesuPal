package com.pesupal.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pesupal.server.model.Users;

@Repository
public interface PeopleRepository extends JpaRepository<Users, Long> {

    public List<Users> findAll();
}
