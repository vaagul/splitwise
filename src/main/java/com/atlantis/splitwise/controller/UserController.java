package com.atlantis.splitwise.controller;

import com.atlantis.splitwise.db.UserRepository;
import com.atlantis.splitwise.model.entity.User;
import com.atlantis.splitwise.resource.UserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserController implements UserResource {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
