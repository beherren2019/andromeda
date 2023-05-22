package com.galaxy.andromeda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IntegrityViolationException extends ResponseStatusException {

    public IntegrityViolationException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
