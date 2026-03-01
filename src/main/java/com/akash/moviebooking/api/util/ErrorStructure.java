package com.akash.moviebooking.api.util;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorStructure {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}