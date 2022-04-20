package com.example.wear_shop.controller;

import com.example.wear_shop.data.DTO.MessageDTO.MessageDTO;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDTO> handleUnKnowException(Exception exception) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessages(exception.getMessage());
        messageDTO.setErrorCode("1");

        return new ResponseEntity(messageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessages(ex.getMessage());
        messageDTO.setErrorCode("2");
        return new ResponseEntity(messageDTO,HttpStatus.BAD_REQUEST);
    }
}
