package com.example.diploma.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    @JsonProperty("auth-token")
    private String authToken;

    public AuthResponse(String authToken) {
        this.authToken = authToken;
    }
}
