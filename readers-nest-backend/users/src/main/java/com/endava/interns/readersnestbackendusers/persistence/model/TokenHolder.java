package com.endava.interns.readersnestbackendusers.persistence.model;

import lombok.Data;

@Data
public class TokenHolder {

    private final String userId;
    private final String token;
    private final String refreshToken;
}
