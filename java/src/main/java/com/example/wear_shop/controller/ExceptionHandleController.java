package com.example.wear_shop.controller;

import com.example.wear_shop.data.DTO.MessageDTO.MessageDTO;
import com.example.wear_shop.data.DTO.fileDTO.ErrorDto;
import org.aspectj.bridge.Message;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDTO> handleUnKnowException(Exception exception) {
        exception.printStackTrace();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessages(exception.getMessage());
        messageDTO.setErrorCode("1");

        return new ResponseEntity(messageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessages(ex.getMessage());
        messageDTO.setErrorCode("2");
        return new ResponseEntity(messageDTO,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorDto> handleFileNotFoundException(FileNotFoundException e) {
        e.printStackTrace();
        ErrorDto errorDto = new ErrorDto(HttpStatus.NOT_FOUND, "Not found", e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageDTO> handleAccessDeniedEx(
           AccessDeniedException ex ) {
        ex.printStackTrace();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessages(ex.getMessage());
        messageDTO.setErrorCode("403");
        return new ResponseEntity(messageDTO,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<MessageDTO> handleAuthenticationEx(
            AuthenticationException ex ) {
        ex.printStackTrace();
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessages(ex.getMessage());
        messageDTO.setErrorCode("401");
        return new ResponseEntity(messageDTO,HttpStatus.UNAUTHORIZED);
    }
}
