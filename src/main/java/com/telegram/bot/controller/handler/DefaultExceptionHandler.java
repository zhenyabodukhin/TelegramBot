package com.telegram.bot.controller.handler;


import com.telegram.bot.controller.messages.ErrorMessage;
import com.telegram.bot.exception.EntityAlreadyExistException;
import com.telegram.bot.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> handleEntityAlreadyExistException(EntityAlreadyExistException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()),
                HttpStatus.FOUND);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessage> handleNPException(NullPointerException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorMessage> handleHttpException(HttpClientErrorException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOthersException(Exception e) {
        /* Handles all other exceptions. Status code 500. */
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorMessage(e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
