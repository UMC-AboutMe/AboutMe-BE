package com.example.aboutme.Login.jwt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtDTO {
    private String accessToken;

    private Long expireIn;
}
