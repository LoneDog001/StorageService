package com.example.diploma.services;

import com.example.diploma.dto.AuthRequest;
import com.example.diploma.security.JwtTokenUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AuthService.class})
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AuthServiceTest {
    @Autowired
    private AuthService authService;
    @MockBean
    private JwtTokenUtils jwtTokenUtils;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private final String USER_NAME = "user";
    private final String PASSWORD = "password";

    private final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(USER_NAME, PASSWORD);
    private final String token = UUID.randomUUID().toString();
    private final AuthRequest authRequest = new AuthRequest(USER_NAME, PASSWORD);

    @Test
    void loginUserTest(){
        when(jwtTokenUtils.generateToken(Mockito.<UserDetails>any())).thenReturn(token);
        assertNotEquals(token, authService.loginUser(authRequest));
    }
}
