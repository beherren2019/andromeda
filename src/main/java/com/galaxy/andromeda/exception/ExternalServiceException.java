package com.galaxy.andromeda.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class ExternalServiceException extends ResponseStatusException {

    public ExternalServiceException(String message, HttpClientErrorException e) {
        super(e.getStatusCode(), message, e);
        log.error(message, e.getMessage());
    }

    public ExternalServiceException(String message, HttpServerErrorException e) {
        super(e.getStatusCode(), message, e);
        log.error(message, e.getMessage());
    }

    public ExternalServiceException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
        log.error(message);
    }
}
