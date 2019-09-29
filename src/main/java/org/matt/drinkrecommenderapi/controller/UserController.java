package org.matt.drinkrecommenderapi.controller;

import org.matt.drinkrecommenderapi.model.UserProfile;
import org.matt.drinkrecommenderapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    final
    UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public List<UserProfile> getUsers() {
        return repository.findAll();
    }

    @PostMapping("/users")
    public UserProfile createUser(@RequestBody UserProfile profile) {
        return repository.save(profile);
    }

}
