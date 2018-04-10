package com.endava.interns.readersnestbackendbooks.response;

import com.endava.interns.readersnestbackendbooks.response.dto.ErrorDTO;
import lombok.Data;

@Data
public class ResponseMessage<T> {

    private T data;
    private ErrorDTO error;

    public ResponseMessage(T data) {
        this.data = data;
    }

    public ResponseMessage(ErrorDTO error) {
        this.error = error;
    }
}
