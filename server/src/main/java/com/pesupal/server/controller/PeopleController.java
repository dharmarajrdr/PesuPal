package com.pesupal.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pesupal.server.model.Users;
import com.pesupal.server.service.PeopleService;

@RestController
@RequestMapping("/api/v1/")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return peopleService.getAllUsers();
    }
}