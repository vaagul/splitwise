package com.atlantis.splitwise.resource;

import com.atlantis.splitwise.model.entity.User;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface UserResource {
    @GetMapping("/user/all")
    List<User> getUsers();
}
