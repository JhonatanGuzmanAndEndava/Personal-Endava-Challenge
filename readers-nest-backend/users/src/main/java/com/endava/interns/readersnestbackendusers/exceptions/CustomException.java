package com.endava.interns.readersnestbackendusers.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class CustomException extends Exception{

    private final String error;
    private final String description;

}
