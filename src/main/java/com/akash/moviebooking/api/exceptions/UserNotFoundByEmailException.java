package com.akash.moviebooking.api.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundByEmailException extends RuntimeException {

    private String message;

    public UserNotFoundByEmailException(String message) {
        this.message = message;
    }
}
