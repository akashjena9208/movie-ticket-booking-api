package com.akash.moviebooking.api.exceptions;

import lombok.Getter;

@Getter
public class CityNotFoundException extends RuntimeException {
    private String message;
    public CityNotFoundException(String message) {
        this.message = message;
    }
}