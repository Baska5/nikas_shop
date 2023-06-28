package com.exadel.nikas_shop.exception.handlers;


import com.exadel.nikas_shop.aspect.AspectConfig;
import com.exadel.nikas_shop.exception.ExceptionResponse;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.exception.exceptions.InvalidStateTransitionException;
import com.exadel.nikas_shop.exception.exceptions.NonUniqueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(AspectConfig.class);

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ExceptionResponse response = new ExceptionResponse(
                e.getMessage(),
                status,
                LocalDateTime.now()
        );
        logger.error("Exception occurred. status: {}, message: {}", status, e.getMessage());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = {NonUniqueException.class})
    public ResponseEntity<ExceptionResponse> handleNonUniqueException(NonUniqueException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ExceptionResponse response = new ExceptionResponse(
                e.getMessage(),
                status,
                LocalDateTime.now()
        );
        logger.error("Exception occurred. status: {}, message: {}", status, e.getMessage());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = {InvalidStateTransitionException.class})
    public ResponseEntity<ExceptionResponse> handleInvalidStateTransitionException(InvalidStateTransitionException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ExceptionResponse response = new ExceptionResponse(
                e.getMessage(),
                status,
                LocalDateTime.now()
        );
        logger.error("Exception occurred. status: {}, message: {}", status, e.getMessage());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(
                e.getMostSpecificCause().getMessage(),
                status,
                LocalDateTime.now()
        );
        logger.error("Exception occurred. status: {}, message: {}", status, e.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(response, status);
    }
}
