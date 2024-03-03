package com.api.videojuegos.controller;

import com.api.videojuegos.dto.JwtAuthenticationResponse;
import com.api.videojuegos.request.LoginRequest;
import com.api.videojuegos.request.RegistroRequest;
import com.api.videojuegos.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegistroRequest request) {
        try {
            logger.info("Received signup request: {}", request);
            JwtAuthenticationResponse response = authenticationService.signup(request);
            logger.info("Signup successful for user: {}", request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during signup process.", e);
            return new ResponseEntity<>("Error during signup process", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest request) {
        try {
            logger.info("Received signin request: {}", request);
            JwtAuthenticationResponse response = authenticationService.signin(request);
            logger.info("Signin successful for user: {}", request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during signin process.", e);
            return new ResponseEntity<>("Error during signin process", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public String logout() {
        logger.info("User has logged out");
        return "Has cerrado sesión correctamente";
    }
}
