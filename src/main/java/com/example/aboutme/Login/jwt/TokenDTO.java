package com.example.aboutme.Login.jwt;

import com.example.aboutme.domain.constant.Social;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    String token;
    String email;

    public TokenDTO(String token) {
        this.token = token;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class tokenClaimsDTO {
        private String email;
        private Social social;
    }

}
