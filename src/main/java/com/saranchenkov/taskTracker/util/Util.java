package com.saranchenkov.taskTracker.util;

import com.saranchenkov.taskTracker.domain.User;
import com.saranchenkov.taskTracker.security.CustomPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Ivan on 18.10.2017.
 */
@Slf4j
public final class Util {
    private Util() {
    }

    public static void printLog(HttpServletRequest req){
        log.info("Method {} with path {}", req.getMethod().toUpperCase(), req.getRequestURI());
    }

    public static Set<GrantedAuthority> getAuthorities(User user){
        Objects.requireNonNull(user);
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_".concat(user.getRole().toString())));
    }

    public static Set<GrantedAuthority> getAuthorities(String authority){
        Objects.requireNonNull(authority);
        return Collections.singleton(new SimpleGrantedAuthority(authority));
    }

    public static CustomPrincipal getCustomPrincipal(HttpServletRequest request){
        return (CustomPrincipal)((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal();
    }
}
