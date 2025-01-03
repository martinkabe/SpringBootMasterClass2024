package org.example.springbootmasterclass.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                ex.getReason() != null ? ex.getReason() : "Unexpected error",
                ex.getStatusCode().value(),
                ZonedDateTime.now(),
                List.of(ex.getReason() != null ? ex.getReason() : "Unknown error")
        );
        return new ResponseEntity<>(apiError, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "Invalid value for parameter: " + ex.getName(),
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now(),
                List.of("Failed to convert value: '" + ex.getValue() + "' to required type: '" + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + "'")
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handle(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        List<String> errors = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "Validation failed for request body", //ex.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ZonedDateTime.now(),
                errors
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ApiError> handle(
            InvalidValueException ex,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ZonedDateTime.now(),
                List.of(ex.getMessage()) // Include the exception message in the errors list
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handle(
            DuplicateResourceException ex,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                ZonedDateTime.now(),
                List.of(ex.getMessage()) // Include the exception message in the errors list
        );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handle(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now(),
                List.of(ex.getMessage()) // Include the exception message in the errors list
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiError> handle(
            InvalidInputException ex,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now(),
                List.of(ex.getMessage()) // Include the exception message in the errors list
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handle(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ZonedDateTime.now(),
                List.of(ex.getMessage()) // Include the exception message in the errors list
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
