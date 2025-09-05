package com.akash.moviebooking.api.exceptions.handler;

import com.akash.moviebooking.api.exceptions.NoOfRowsExceedCapacityException;
import com.akash.moviebooking.api.exceptions.ScreenNotFoundByIdException;
import com.akash.moviebooking.api.util.ErrorStructure;
import com.akash.moviebooking.api.util.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class ScreenExceptionHandler {

    private final RestResponseBuilder responseBuilder;

    @ExceptionHandler
    public ResponseEntity<ErrorStructure> handleNoOfRowsExceedCapacityException(NoOfRowsExceedCapacityException ex) {
        return responseBuilder.error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorStructure> handleScreenNotFoundByIdException(ScreenNotFoundByIdException ex) {
        return responseBuilder.error(HttpStatus.NOT_FOUND, ex.getMessage());
    }


}

