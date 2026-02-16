//package com.akash.moviebooking.api.exceptions.handler;
//
//import com.akash.moviebooking.api.exceptions.ResourceNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    // Handle Resource Not Found
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put("timestamp", LocalDateTime.now());
//        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
//        errorResponse.put("error", "Not Found");
//        errorResponse.put("message", ex.getMessage());
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    // Handle All Other Exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put("timestamp", LocalDateTime.now());
//        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        errorResponse.put("error", "Internal Server Error");
//        errorResponse.put("message", ex.getMessage());
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}

package com.akash.moviebooking.api.exceptions.handler;

import com.akash.moviebooking.api.util.ErrorStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===============================
    // IllegalArgumentException
    // ===============================
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorStructure> handleIllegalArgument(
            IllegalArgumentException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorStructure.builder()
                                .statusCode(400)
                                .errorMessage(ex.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                        "statusCode", 403,
                        "errorMessage", "Access Denied"
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorStructure> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorStructure.builder()
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errorMessage(ex.getMessage())
                        .build());
    }
}
