package com.drekerd.testCard.utils.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> suberrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus httpStatus) {
        this();
        this.status = httpStatus;
    }

    ApiError(HttpStatus httpStatus, Throwable ex) {
        this();
        this.status = httpStatus;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus httpStatus, String errorMessage, Throwable ex) {
        this.status = httpStatus;
        this.message = errorMessage;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
