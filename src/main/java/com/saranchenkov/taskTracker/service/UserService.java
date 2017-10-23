package com.saranchenkov.taskTracker.service;

import com.saranchenkov.taskTracker.domain.User;
import com.saranchenkov.taskTracker.mail.EmailHtmlSender;
import com.saranchenkov.taskTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ivan on 23.10.2017.
 */

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;
    private final EmailHtmlSender emailHtmlSender;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, Environment env, EmailHtmlSender emailHtmlSender) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
        this.emailHtmlSender = emailHtmlSender;
    }

    public List<User> searchUsers(String firstName, String lastName){
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        return userRepository.findDevelopersByName(firstName.trim(), lastName.trim());
    }

    @Transactional
    public boolean signup(User newUser) {
        Objects.requireNonNull(newUser);
        User user = userRepository.findByEmail(newUser.getEmail());
        if (Objects.isNull(user)) {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            User savedUser = userRepository.save(newUser);

            Context context = new Context();
            context.setVariable("link",
                    env.getProperty("frontend-server-url") + "/confirm/" + getEncodedId(savedUser.getId()));
            emailHtmlSender.send(savedUser.getEmail(), "Confirmation", "confirmation", context);
            return true;
        }
        return false;
    }

    private static String getEncodedId(int id){
        return Base64.getEncoder().encodeToString(String.valueOf(id).getBytes());
    }
}
