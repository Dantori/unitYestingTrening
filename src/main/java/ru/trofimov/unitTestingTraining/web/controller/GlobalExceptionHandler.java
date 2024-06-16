package ru.trofimov.unitTestingTraining.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.trofimov.unitTestingTraining.exception.ResourceAlreadyExistsException;
import ru.trofimov.unitTestingTraining.exception.ResourceNotFoundException;
import ru.trofimov.unitTestingTraining.exception.utils.AppError;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), e.getMessage()), HttpStatus.CONFLICT);
    }
}
