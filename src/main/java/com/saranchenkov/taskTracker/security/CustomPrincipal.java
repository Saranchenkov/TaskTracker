package com.saranchenkov.taskTracker.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Ivan on 22.10.2017.
 */
@Getter
@AllArgsConstructor
@ToString
public class CustomPrincipal {
    private int id;
    private String email;
}