package com.example.aboutme.Login.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
