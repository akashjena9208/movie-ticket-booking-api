package com.akash.moviebooking.api.exceptions.handler;

import com.akash.moviebooking.api.exceptions.ShowTimeConflictException;
import com.akash.moviebooking.api.util.ErrorStructure;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class ShowExceptionHandler {

    private final RestResponseBuilder responseBuilder;

    @ExceptionHandler
    public ResponseEntity<ErrorStructure> handleShowTimeConflictException(ShowTimeConflictException ex) {
        return responseBuilder.error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

}
