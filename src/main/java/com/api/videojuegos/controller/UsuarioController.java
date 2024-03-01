package com.api.videojuegos.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.videojuegos.dto.UsuarioResponse;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.service.UsuarioService;

/**
 * Controlador para las operaciones relacionadas con usuarios.
 */
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    
    // Inicialización del logger para el controlador de usuarios
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    // Inyección de dependencias del servicio de usuarios
    @Autowired
    UsuarioService usuarioService;

    /**
     * Obtiene la lista de todos los usuarios. Requiere el rol 'ROLE_ADMIN'.
     * @return Lista de respuestas de usuario.
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UsuarioResponse> getAllUsuarios() {
        // Registro de información sobre la solicitud de obtención de todos los usuarios
        logger.info("Obteniendo todos los usuarios");
        // Llamada al servicio de usuarios para obtener la lista de todos los usuarios
        return usuarioService.getAllUsers();
    }

    /**
     * Obtiene un usuario por su ID. Requiere el rol 'ROLE_ADMIN'.
     * @param id ID del usuario a obtener.
     * @return Respuesta de usuario correspondiente al ID proporcionado.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UsuarioResponse getUsuario(@PathVariable Long id) {
        // Registro de información sobre la solicitud de obtención de un usuario por ID
        logger.info("Obteniendo usuario por ID: {}", id);
        // Llamada al servicio de usuarios para obtener un usuario por ID
        return usuarioService.findUserById(id);
    }

    /**
     * Crea un nuevo usuario. Requiere el rol 'ROLE_ADMIN'.
     * @param usuario Objeto Usuario que contiene la información del nuevo usuario.
     * @return El usuario creado.
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        // Registro de información sobre la solicitud de creación de un nuevo usuario
        logger.info("Creando un nuevo usuario");
        // Llamada al servicio de usuarios para crear un nuevo usuario
        return usuarioService.createUser(usuario);
    }
}
