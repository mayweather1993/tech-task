package com.mayweather.techtask.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class SynergyJuniorKnowsHowToHandleExceptionsHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<NotFoundYourObjectException> handleThereAreNoSuchObject() {
        return new ResponseEntity<>(new NotFoundYourObjectException("There is no your object"), NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class NotFoundYourObjectException {
        private String message;
    }
}
