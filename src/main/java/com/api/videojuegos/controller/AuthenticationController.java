package com.api.videojuegos.controller;

import com.api.videojuegos.dto.JwtAuthenticationResponse;
import com.api.videojuegos.entity.Rol;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.request.LoginRequest;
import com.api.videojuegos.request.RegistroRequest;
import com.api.videojuegos.service.AuthenticationService;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para las operaciones de autenticación (signup, signin, logout).
 */

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AuthenticationService authenticationService;

    /**
     * Registra un nuevo usuario en el sistema.
     * @param request Objeto RegistroRequest con los datos del nuevo usuario.
     * @return Respuesta de autenticación JWT.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegistroRequest request) {
        try {
            // Crear un nuevo usuario y asignarle el rol ROLE_USER por defecto
            Usuario usuario = new Usuario();
            usuario.setEmail(request.getEmail());
            usuario.setFirstName(request.getNombre());
            usuario.setLastName(request.getApellidos());
            usuario.setPassword(request.getPassword());
            usuario.setRoles(Collections.singleton(Rol.ROLE_USER)); 
            
            // Llamar al servicio de autenticación para registrar el usuario
            JwtAuthenticationResponse response = authenticationService.signup(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during signup process.", e);
            return new ResponseEntity<>("Error during signup process", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Inicia sesión para un usuario existente.
     * @param request Objeto LoginRequest con las credenciales de inicio de sesión.
     * @return Respuesta de autenticación JWT.
     */
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

    /**
     * Cierra la sesión del usuario actual.
     * @return Mensaje de confirmación de cierre de sesión.
     */
    @PostMapping("/logout")
    public String logout() {
        logger.info("User has logged out");
        return "Has cerrado sesión correctamente";
    }
}
