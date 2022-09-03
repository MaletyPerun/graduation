package ru.graduation.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    DATA_ERROR(HttpStatus.CONFLICT),
    VALIDATION_ERROR(HttpStatus.UNPROCESSABLE_ENTITY);
    private final HttpStatus status;

    ErrorType(HttpStatus status) {
        this.status = status;
    }
}
