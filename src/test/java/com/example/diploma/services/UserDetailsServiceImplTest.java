package com.example.diploma.services;

import com.example.diploma.entities.User;
import com.example.diploma.model.SecurityUser;
import com.example.diploma.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private UserRepository userRepository;
    private final String USER_NAME = "admin";
    private final String PASSWORD = "password";
    private final User user = new User(USER_NAME, PASSWORD, null);
    private final SecurityUser securityUser = new SecurityUser(user);

    @Test
    void loadUserByUserNameTest(){
        given(userRepository.findByUserName(USER_NAME)).willReturn(Optional.of(user));
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_NAME);
        assertEquals(securityUser.getPassword(), userDetails.getPassword());
    }
}
