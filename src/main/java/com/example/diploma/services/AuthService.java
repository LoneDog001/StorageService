package com.example.diploma.services;

import com.example.diploma.dto.AuthRequest;
import com.example.diploma.dto.AuthResponse;
import com.example.diploma.security.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final Map<String, String> tokenStore = new HashMap<>();

    public AuthService(AuthenticationManager authenticationManager, JwtTokenUtils jwtTokenUtils, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userDetailsService = userDetailsService;
    }

    public AuthResponse loginUser(AuthRequest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Bad credentials");
        }
        String login = authRequest.getLogin();
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        String token = jwtTokenUtils.generateToken(userDetails);
        tokenStore.put(login, token);
        return new AuthResponse(token);
    }

    public void logoutUser(String authToken){
        tokenStore.remove(authToken);
    }
}
