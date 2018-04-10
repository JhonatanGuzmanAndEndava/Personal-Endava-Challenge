package com.endava.interns.readersnestbackendbooks.persistence.entities;

import lombok.Data;

@Data
public class TokenHolder {

    private final String userId;
    private final String token;
    private final String refreshToken;
}
