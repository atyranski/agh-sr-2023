package edu.agh.atyranski.server.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class CommitExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, DateTimeParseException.class })
    protected ResponseEntity<Object> handlerBadRequest(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { UnknownFileStatus.class })
    protected ResponseEntity<Object> handlerFailedDependency(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY, request);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handlerInternalServerError(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, "lol, idk what happened: " + ex.getMessage(), new HttpHeaders(), HttpStatus.I_AM_A_TEAPOT, request);
    }
}
