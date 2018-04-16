package com.endava.interns.readersnestbackendbooks.response.dto;

import com.endava.interns.readersnestbackendbooks.persistence.entities.TokenHolder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TokenHolderDTO {

    private String userId;
    private String token;
    private String refreshToken;

    public static TokenHolderDTO entityToDTO(TokenHolder tokenHolder){

        return new TokenHolderDTO()
                .setUserId(tokenHolder.getUserId())
                .setToken(tokenHolder.getToken())
                .setRefreshToken(tokenHolder.getRefreshToken());
    }

}
