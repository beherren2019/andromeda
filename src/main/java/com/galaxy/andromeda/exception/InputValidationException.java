package com.galaxy.andromeda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InputValidationException extends ResponseStatusException {

    public InputValidationException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }
}
