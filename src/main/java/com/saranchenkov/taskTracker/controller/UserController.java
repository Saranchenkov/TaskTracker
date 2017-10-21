package com.saranchenkov.taskTracker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.saranchenkov.taskTracker.domain.User;
import com.saranchenkov.taskTracker.jsonViews.TaskViews;
import com.saranchenkov.taskTracker.repository.UserRepository;
import com.saranchenkov.taskTracker.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ivan on 19.10.2017.
 */

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @JsonView(TaskViews.ExtendedTask.class)
    public ResponseEntity search(@RequestParam("first") String firstName, @RequestParam("last") String lastName, HttpServletRequest request){
        Util.printLog(request);
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        List<User> users = userRepository.findDevelopersByName(firstName.trim(), lastName.trim());
        return users.size() > 0 ?
                new ResponseEntity<>(users, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody User newUser, HttpServletRequest request){
        Util.printLog(request);
        Objects.requireNonNull(newUser);
        User user = userRepository.findByEmail(newUser.getEmail());
        if (Objects.isNull(user)) {
            userRepository.save(newUser);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}
