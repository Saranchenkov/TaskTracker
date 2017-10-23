package com.saranchenkov.taskTracker.service;

import com.saranchenkov.taskTracker.domain.User;
import com.saranchenkov.taskTracker.repository.UserRepository;
import com.saranchenkov.taskTracker.security.CustomUser;
import com.saranchenkov.taskTracker.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by Ivan on 21.10.2017.
 */

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("Load user with email: {} from DB.", email);
        User user = userRepository.findByEmail(email);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(email);
        }

        return new CustomUser(user.getEmail(), user.getPassword(), user.isEnabled(),
                true, true, true, Util.getAuthorities(user), user.getId());
    }
}
