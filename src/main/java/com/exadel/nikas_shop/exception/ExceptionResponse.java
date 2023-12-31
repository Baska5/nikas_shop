package com.exadel.nikas_shop.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private final String message;
    private final HttpStatus status;
    private final LocalDateTime localDateTime;
}
