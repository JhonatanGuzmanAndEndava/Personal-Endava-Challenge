package com.endava.interns.readersnestbackendbookclubs.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class CustomException extends Exception{

    protected final String error;
    protected final String description;

}
