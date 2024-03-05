package com.api.videojuegos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.api.videojuegos.controller.AuthenticationController;
import com.api.videojuegos.dto.JwtAuthenticationResponse;
import com.api.videojuegos.entity.Rol;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.request.LoginRequest;
import com.api.videojuegos.request.RegistroRequest;
import com.api.videojuegos.service.AuthenticationService;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void signup_shouldReturnJwtAuthenticationResponse() {
        // Arrange
        RegistroRequest registroRequest = new RegistroRequest();
        registroRequest.setEmail("test@example.com");
        registroRequest.setNombre("Test");
        registroRequest.setApellidos("User");
        registroRequest.setPassword("test123");

        JwtAuthenticationResponse mockResponse = new JwtAuthenticationResponse("token");
        when(authenticationService.signup(registroRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<?> response = authenticationController.signup(registroRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void signin_shouldReturnJwtAuthenticationResponse() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("test123");

        JwtAuthenticationResponse mockResponse = new JwtAuthenticationResponse("token");
        when(authenticationService.signin(loginRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<?> response = authenticationController.signin(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }
}

