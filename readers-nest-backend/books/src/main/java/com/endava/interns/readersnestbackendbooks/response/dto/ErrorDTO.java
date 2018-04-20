package com.endava.interns.readersnestbackendbooks.response.dto;

import com.endava.interns.readersnestbackendbooks.exceptions.CustomException;
import lombok.Data;

@Data
public class ErrorDTO {

    private final String error;
    private final String description;

    public ErrorDTO(CustomException e) {
        this.error = e.getError();
        this.description = e.getDescription();
    }
}
