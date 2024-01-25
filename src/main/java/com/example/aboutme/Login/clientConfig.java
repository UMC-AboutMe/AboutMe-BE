package com.example.aboutme.Login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class clientConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
