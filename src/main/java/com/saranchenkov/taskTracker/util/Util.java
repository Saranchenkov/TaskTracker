package com.saranchenkov.taskTracker.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

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
}
