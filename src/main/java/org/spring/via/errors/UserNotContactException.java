package org.spring.via.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotContactException extends RuntimeException {
    public UserNotContactException(String message) {
        super(message);
    }
}
