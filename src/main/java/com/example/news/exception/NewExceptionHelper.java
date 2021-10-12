package com.example.news.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class NewExceptionHelper {
    @ExceptionHandler(value = {NewNotFoundException.class})
    public ResponseEntity<Object> handleNewNotFoundException(NewNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setTimestamp(Instant.now().toEpochMilli());
        exceptionResponse.setStatus(404);
        exceptionResponse.setError("Not Found");
        exceptionResponse.setMessage("NOT_FOUND");
        exceptionResponse.setPath("/news");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { HeadlineAlreadyExistsException.class })
    public ResponseEntity<Object> handleHeadlineAlreadyExistsException(HeadlineAlreadyExistsException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setTimestamp(Instant.now().toEpochMilli());
        exceptionResponse.setStatus(400);
        exceptionResponse.setError("Bad Request");
        exceptionResponse.setMessage("HEADLINE_ALREADY_EXISTS");
        exceptionResponse.setPath("/news");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { SummaryAlreadyExistsException.class })
    public ResponseEntity<Object> handleSummaryAlreadyExistsException(SummaryAlreadyExistsException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setTimestamp(Instant.now().toEpochMilli());
        exceptionResponse.setStatus(400);
        exceptionResponse.setError("Bad Request");
        exceptionResponse.setMessage("SUMMARY_ALREADY_EXISTS");
        exceptionResponse.setPath("/news");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { BodyAlreadyExistsException.class })
    public ResponseEntity<Object> handleBodyAlreadyExistsException(BodyAlreadyExistsException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setTimestamp(Instant.now().toEpochMilli());
        exceptionResponse.setStatus(400);
        exceptionResponse.setError("Bad Request");
        exceptionResponse.setMessage("BODY_ALREADY_EXISTS");
        exceptionResponse.setPath("/news");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
