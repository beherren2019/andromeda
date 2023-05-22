package com.galaxy.andromeda.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class NoSearchCriteriaAvailableException extends ResponseStatusException {

    public NoSearchCriteriaAvailableException(String message) {
        super(HttpStatus.CONFLICT, message);
        log.error(message);
    }
}
