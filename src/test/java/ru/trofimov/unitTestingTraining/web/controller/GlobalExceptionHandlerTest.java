package ru.trofimov.unitTestingTraining.web.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.trofimov.unitTestingTraining.exception.ResourceAlreadyExistsException;
import ru.trofimov.unitTestingTraining.exception.ResourceNotFoundException;
import ru.trofimov.unitTestingTraining.exception.utils.AppError;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void test_catchResourceNotFoundException() {
        Assertions.assertEquals(
                new ResponseEntity<>(
                        new AppError(HttpStatus.NOT_FOUND.value(), "Resource not found"),
                        HttpStatus.NOT_FOUND),
                globalExceptionHandler.catchResourceNotFoundException(
                        new ResourceNotFoundException("Resource not found")
                )
        );
    }

    @Test
    void test_catchResourceAlreadyExistsException() {
        Assertions.assertEquals(
                new ResponseEntity<>(
                        new AppError(HttpStatus.CONFLICT.value(), "Resource already exists"),
                        HttpStatus.CONFLICT),
                globalExceptionHandler.catchResourceAlreadyExistsException(
                        new ResourceAlreadyExistsException("Resource already exists")
                )
        );
    }
}
