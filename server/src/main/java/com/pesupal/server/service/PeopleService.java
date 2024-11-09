package com.pesupal.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pesupal.server.model.Users;
import com.pesupal.server.repository.PeopleRepository;

@Service
public class PeopleService {

    @Autowired
    PeopleRepository peopleRepository;

    public List<Users> getAllUsers() {
        return peopleRepository.findAll();
    }

}
