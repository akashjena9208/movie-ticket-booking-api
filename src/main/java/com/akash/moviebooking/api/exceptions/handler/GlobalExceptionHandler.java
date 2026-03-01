package com.akash.moviebooking.api.exceptions.handler;

import com.akash.moviebooking.api.exceptions.*;
import com.akash.moviebooking.api.util.ErrorStructure;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    // ==========================================================
//    // 🔐 AUTHENTICATION EXCEPTIONS (401)
//    // ==========================================================
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ErrorStructure> handleAuthenticationException(
//            AuthenticationException ex,
//            HttpServletRequest request) {
//
//        return buildResponse(
//                HttpStatus.UNAUTHORIZED,
//                ex.getMessage(),
//                request
//        );
//    }
//
//    // ==========================================================
//    // 🔐 AUTHORIZATION EXCEPTIONS (403)
//    // ==========================================================
//    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
//    public ResponseEntity<ErrorStructure> handleAccessDenied(
//            Exception ex,
//            HttpServletRequest request) {
//
//        return buildResponse(
//                HttpStatus.FORBIDDEN,
//                "You do not have permission to access this resource",
//                request
//        );
//    }
//
//    // ==========================================================
//    // 📌 BUSINESS EXCEPTIONS - NOT FOUND (404)
//    // ==========================================================
//    @ExceptionHandler({
//            UserNotFoundByEmailException.class,
//            MovieNotFoundByIdException.class,
//            TheaterNotFoundByIdException.class,
//            ScreenNotFoundByIdException.class,
//            BookingNotFoundException.class,
//            ResourceNotFoundException.class,
//            CityNotFoundException.class
//    })
//    public ResponseEntity<ErrorStructure> handleNotFoundExceptions(
//            RuntimeException ex,
//            HttpServletRequest request) {
//
//        return buildResponse(
//                HttpStatus.NOT_FOUND,
//                ex.getMessage(),
//                request
//        );
//    }
//
//    // ==========================================================
//    // 📌 BUSINESS EXCEPTIONS - BAD REQUEST (400)
//    // ==========================================================
//    @ExceptionHandler({
//            ShowTimeConflictException.class,
//            NoOfRowsExceedCapacityException.class,
//            IllegalArgumentException.class
//    })
//    public ResponseEntity<ErrorStructure> handleBadRequestExceptions(
//            RuntimeException ex,
//            HttpServletRequest request) {
//
//        return buildResponse(
//                HttpStatus.BAD_REQUEST,
//                ex.getMessage(),
//                request
//        );
//    }
//
//    // ==========================================================
//    // 📌 DUPLICATE / CONFLICT (409)
//    // ==========================================================
//    @ExceptionHandler({
//            UserExistByEmailException.class
//    })
//    public ResponseEntity<ErrorStructure> handleConflictExceptions(
//            RuntimeException ex,
//            HttpServletRequest request) {
//
//        return buildResponse(
//                HttpStatus.CONFLICT,
//                ex.getMessage(),
//                request
//        );
//    }
//
//    // ==========================================================
//    // 📌 VALIDATION - @Valid RequestBody
//    // ==========================================================
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorStructure> handleValidation(
//            MethodArgumentNotValidException ex,
//            HttpServletRequest request) {
//
//        String message = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .collect(Collectors.joining(", "));
//
//        return buildResponse(
//                HttpStatus.BAD_REQUEST,
//                message,
//                request
//        );
//    }
//
//    // ==========================================================
//    // 📌 VALIDATION - @RequestParam / @PathVariable
//    // ==========================================================
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ErrorStructure> handleConstraintViolation(
//            ConstraintViolationException ex,
//            HttpServletRequest request) {
//
//        return buildResponse(
//                HttpStatus.BAD_REQUEST,
//                ex.getMessage(),
//                request
//        );
//    }
//
//    // ==========================================================
//    // 📌 INVALID JSON FORMAT
//    // ==========================================================
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<ErrorStructure> handleInvalidJson(
//            HttpMessageNotReadableException ex,
//            HttpServletRequest request) {
//
//        return buildResponse(
//                HttpStatus.BAD_REQUEST,
//                "Invalid request body format",
//                request
//        );
//    }
//
//    // ==========================================================
//    // 🗄 DATABASE CONSTRAINT VIOLATION
//    // ==========================================================
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ErrorStructure> handleDatabaseError(
//            DataIntegrityViolationException ex,
//            HttpServletRequest request) {
//
//        return buildResponse(
//                HttpStatus.CONFLICT,
//                "Database constraint violation",
//                request
//        );
//    }
//
//    // ==========================================================
//    // 🌍 GENERIC FALLBACK (500)
//    // ==========================================================
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorStructure> handleGeneralException(
//            Exception ex,
//            HttpServletRequest request) {
//
//        return buildResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "Something went wrong. Please try again later.",
//                request
//        );
//    }
//
//    // ==========================================================
//    // 🔧 COMMON RESPONSE BUILDER
//    // ==========================================================
//    private ResponseEntity<ErrorStructure> buildResponse(
//            HttpStatus status,
//            String message,
//            HttpServletRequest request) {
//
//        String path = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
//        if (path == null) {
//            path = request.getRequestURI();
//        }
//
//        ErrorStructure error = ErrorStructure.builder()
//                .timestamp(LocalDateTime.now())
//                .status(status.value())
//                .error(status.getReasonPhrase())
//                .message(message)
//                .path(path)
//                .build();
//
//        return new ResponseEntity<>(error, status);
//    }
//}

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ================= AUTH =================
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorStructure> handleAuth(
            AuthenticationException ex,
            HttpServletRequest request) {

        return build(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
    public ResponseEntity<ErrorStructure> handleAccess(
            Exception ex,
            HttpServletRequest request) {

        return build(HttpStatus.FORBIDDEN,
                "You do not have permission to access this resource",
                request);
    }

    // ================= NOT FOUND =================
    @ExceptionHandler({
            UserNotFoundByEmailException.class,
            MovieNotFoundByIdException.class,
            TheaterNotFoundByIdException.class,
            ScreenNotFoundByIdException.class,
            BookingNotFoundException.class,
            PaymentNotFoundException.class,
            ResourceNotFoundException.class,
            CityNotFoundException.class
    })
    public ResponseEntity<ErrorStructure> handleNotFound(
            RuntimeException ex,
            HttpServletRequest request) {

        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    // ================= BAD REQUEST =================
    @ExceptionHandler({
            ShowTimeConflictException.class,
            NoOfRowsExceedCapacityException.class,
            BadRequestException.class,
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public ResponseEntity<ErrorStructure> handleBadRequest(
            RuntimeException ex,
            HttpServletRequest request) {

        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    // ================= CONFLICT =================
    @ExceptionHandler(UserExistByEmailException.class)
    public ResponseEntity<ErrorStructure> handleConflict(
            RuntimeException ex,
            HttpServletRequest request) {

        return build(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    // ================= VALIDATION =================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorStructure> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return build(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorStructure> handleConstraint(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    // ================= DATABASE =================
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorStructure> handleDB(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        return build(HttpStatus.CONFLICT,
                "Database constraint violation",
                request);
    }

    // ================= JSON =================
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorStructure> handleJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        return build(HttpStatus.BAD_REQUEST,
                "Invalid request body format",
                request);
    }

    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    public ResponseEntity<ErrorStructure> handleResponseStatusException(
            org.springframework.web.server.ResponseStatusException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.valueOf(ex.getStatusCode().value()),
                ex.getReason(),
                request
        );
    }

    // ================= METHOD NOT ALLOWED (405) =================
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorStructure> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        return build(
                HttpStatus.METHOD_NOT_ALLOWED,
                "HTTP method not supported for this endpoint.",
                request
        );
    }

    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorStructure> handleMissingParam(
            org.springframework.web.bind.MissingServletRequestParameterException ex,
            HttpServletRequest request) {

        return build(HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request);
    }

    // ================= FALLBACK =================
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorStructure> handleGeneric(
//            Exception ex,
//            HttpServletRequest request) {
//
//        return build(HttpStatus.INTERNAL_SERVER_ERROR,
//                "Something went wrong. Please try again later.",
//                request);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorStructure> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        ex.printStackTrace();   // 👈 ADD THIS LINE

        return build(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),   // 👈 CHANGE THIS (show real message)
                request);
    }

    private ResponseEntity<ErrorStructure> build(
            HttpStatus status,
            String message,
            HttpServletRequest request) {

        ErrorStructure error = ErrorStructure.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }
}