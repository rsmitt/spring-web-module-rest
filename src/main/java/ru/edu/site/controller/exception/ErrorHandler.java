package ru.edu.site.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.edu.site.exception.ServiceError;
import ru.edu.site.exception.UserAlreadyExistsException;

import java.util.Date;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ServiceError> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        ServiceError error = new ServiceError();
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setTimeStamp(new Date().getTime());
        error.setDetails(exception.getClass().getName());
        error.setMessage(exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
