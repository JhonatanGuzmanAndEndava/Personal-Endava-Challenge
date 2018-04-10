package com.endava.interns.readersnestbackendusers.response;

import com.endava.interns.readersnestbackendusers.response.dto.ErrorDTO;
import lombok.Data;

@Data
public class ResponseMessage<T> {

    private T data;
    private ErrorDTO error;

    public ResponseMessage(T data) {
        this.data = data;
    }

    public ResponseMessage(ErrorDTO errorDTO) {
        this.error = errorDTO;
    }
}
