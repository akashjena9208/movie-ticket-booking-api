//package com.akash.moviebooking.api.exceptions.handler;
//
//import com.akash.moviebooking.api.util.ErrorStructure;
//import com.akash.moviebooking.api.util.RestResponseBuilder;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//@AllArgsConstructor
//public class LoginExceptionHandler {
//
//    private final RestResponseBuilder responseBuilder;
//
//    @ExceptionHandler
//    ResponseEntity<ErrorStructure> handleUsernameNotFoundException(UsernameNotFoundException exception){
//        return responseBuilder.error(HttpStatus.UNAUTHORIZED, "No Username Found");
//    }
//
//}
