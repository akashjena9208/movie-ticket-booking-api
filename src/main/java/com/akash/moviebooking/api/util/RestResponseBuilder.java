//package com.akash.moviebooking.api.util;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//public class RestResponseBuilder {
//
//    public <T> ResponseEntity<ResponseStructure<T>> success(HttpStatus status,
//                                                            String message,
//                                                            T data) {
//
//        return ResponseEntity.status(status)
//                .body(ResponseStructure.<T>builder()
//                        .status(status.value())
//                        .message(message)
//                        .data(data)
//                        .build());
//    }
//
//    public ResponseEntity<ErrorStructure> error(HttpStatus status,
//                                                String message,
//                                                String path) {
//
//        return ResponseEntity.status(status)
//                .body(ErrorStructure.builder()
//                        .timestamp(LocalDateTime.now())
//                        .status(status.value())
//                        .error(status.getReasonPhrase())
//                        .message(message)
//                        .path(path)
//                        .build());
//    }
//}
package com.akash.moviebooking.api.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RestResponseBuilder {

    public <T> ResponseEntity<ApiResponse<T>> success(
            HttpStatus status,
            String message,
            T data,
            HttpServletRequest request) {

        return ResponseEntity.status(status)
                .body(ApiResponse.<T>builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .success(true)
                        .message(message)
                        .data(data)
                        .path(request.getRequestURI())
                        .build());
    }

    public ResponseEntity<ApiResponse<Object>> error(
            HttpStatus status,
            String message,
            HttpServletRequest request) {

        return ResponseEntity.status(status)
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .success(false)
                        .message(message)
                        .path(request.getRequestURI())
                        .build());
    }

    public ResponseEntity<ApiResponse<Object>> validationError(
            HttpStatus status,
            String message,
            Map<String, String> errors,
            HttpServletRequest request) {

        return ResponseEntity.status(status)
                .body(ApiResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .success(false)
                        .message(message)
                        .errors(errors)
                        .path(request.getRequestURI())
                        .build());
    }
}