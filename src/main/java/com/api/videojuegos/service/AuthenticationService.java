package com.api.videojuegos.service;

import com.api.videojuegos.dto.JwtAuthenticationResponse;
import com.api.videojuegos.request.LoginRequest;
import com.api.videojuegos.request.RegistroRequest;

public interface AuthenticationService {
    
    /** REGISTRO */
    JwtAuthenticationResponse signup(RegistroRequest request);
    
    /** ACCESO a Token JWT */
    JwtAuthenticationResponse signin(LoginRequest request);
}
