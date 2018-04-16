package com.endava.interns.readersnestbackendusers.controllers;

import com.endava.interns.readersnestbackendusers.exceptions.CustomException;
import com.endava.interns.readersnestbackendusers.response.ResponseMessage;
import com.endava.interns.readersnestbackendusers.response.dto.ErrorDTO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@RequestMapping(produces = "application/json")
public class ControllerErrorHandler {

    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public ResponseMessage customExceptionHandler(CustomException e){
        return new ResponseMessage(new ErrorDTO(e.getError(), e.getDescription()));
    }
}
