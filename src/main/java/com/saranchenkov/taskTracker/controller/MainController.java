package com.saranchenkov.taskTracker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.saranchenkov.taskTracker.domain.User;
import com.saranchenkov.taskTracker.jsonViews.TaskViews;
import com.saranchenkov.taskTracker.repository.UserRepository;
import com.saranchenkov.taskTracker.service.UserService;
import com.saranchenkov.taskTracker.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Ivan on 19.10.2017.
 */

@RestController
@Slf4j
public class MainController {

    private final UserService service;
    private final UserRepository userRepository;

    @Autowired
    public MainController(UserService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @GetMapping("/users/search")
    @JsonView(TaskViews.ExtendedTask.class)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity search(@RequestParam("first") String firstName, @RequestParam("last") String lastName, HttpServletRequest request){
        Util.printLog(request);
        List<User> users = service.searchUsers(firstName, lastName);
        return users.size() > 0 ?
                new ResponseEntity<>(users, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody User newUser, HttpServletRequest request){
        Util.printLog(request);
        return service.signup(newUser) ?
                new ResponseEntity(HttpStatus.CREATED) :
                new ResponseEntity(HttpStatus.CONFLICT);
    }

    @PatchMapping("/confirm")
    public ResponseEntity confirm(@RequestBody int id){
        log.info("Enable user with id: {}", id);
        userRepository.enableUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
