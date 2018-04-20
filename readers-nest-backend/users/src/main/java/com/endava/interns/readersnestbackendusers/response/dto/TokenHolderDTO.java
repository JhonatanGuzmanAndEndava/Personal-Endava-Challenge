package com.endava.interns.readersnestbackendusers.response.dto;

import com.endava.interns.readersnestbackendusers.persistence.model.TokenHolder;
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
