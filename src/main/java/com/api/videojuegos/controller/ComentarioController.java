package com.api.videojuegos.controller;

import com.api.videojuegos.dto.ComentarioRequest;
import com.api.videojuegos.entity.Comentario;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.service.ComentarioService;
import com.api.videojuegos.service.UsuarioService;
import com.api.videojuegos.service.VideojuegosService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * Controlador para las operaciones relacionadas con los comentarios.
 */

@RestController
@RequestMapping("/api/v1/comentarios")
public class ComentarioController {

    private static final Logger logger = LoggerFactory.getLogger(ComentarioController.class);

    @Autowired
    ComentarioService comentarioService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    VideojuegosService videojuegosService;

    
    /**
     * Obtiene todos los comentarios.
     * @return Lista de todos los comentarios.
     */
    @GetMapping
    public ResponseEntity<List<Comentario>> getAllComentarios() {
        try {
            List<Comentario> comentarios = comentarioService.getAllComments();
            logger.info("Returned {} comentarios.", comentarios.size());
            return new ResponseEntity<>(comentarios, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting all comments.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene un comentario por su ID.
     * @param id ID del comentario a obtener.
     * @return Comentario correspondiente al ID proporcionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comentario> getComentarioById(@PathVariable Long id) {
        try {
            Comentario comentario = comentarioService.findById(id);
            logger.info("Returned comment with ID: {}", id);
            return new ResponseEntity<>(comentario, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting comment with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Agrega un nuevo comentario.
     * @param comentarioRequest Objeto ComentarioRequest con la información del nuevo comentario.
     * @return Respuesta de éxito.
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> addComentario(@RequestBody ComentarioRequest comentarioRequest) {
        try {
            // Obtener el usuario basado en el nombre proporcionado en la solicitud
            Optional<Usuario> usuarioOptional = usuarioService.findByEmail(comentarioRequest.getUser());
            Usuario usuario = usuarioOptional.orElse(null);
            if (usuario == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Manejar el caso en que el usuario no exista
            }

            // Obtener el videojuego basado en el nombre proporcionado en la solicitud
            Optional<Videojuegos> videojuegoOptional = videojuegosService.findByNombre(comentarioRequest.getGame());
            Videojuegos videojuego = videojuegoOptional.orElse(null);
            if (videojuego == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Manejar el caso en que el videojuego no exista
            }

            // Crear un nuevo objeto de Comentario y configurar el usuario y el videojuego
            Comentario comentario = new Comentario();
            comentario.setText(comentarioRequest.getText());
            comentario.setUsuario(usuario);
            comentario.setVideojuegos(videojuego);

            // Guardar el comentario en la base de datos
            comentarioService.addComment(comentario);
            logger.info("Added new comment with ID: {}", comentario.getId());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while adding a new comment.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    /**
     * Actualiza un comentario existente por su ID.
     * Requiere el rol 'ROLE_USER'.
     * @param id ID del comentario a actualizar.
     * @param comentarioRequest Objeto ComentarioRequest con los datos actualizados del comentario.
     * @return Respuesta de éxito.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> updateComentario(@PathVariable Long id, @RequestBody ComentarioRequest comentarioRequest) {
        try {
            // Buscar el comentario por su ID
            Comentario comentarioExistente = comentarioService.findById(id);
            
            // Verificar si el comentario existe
            if (comentarioExistente == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            // Actualizar el texto del comentario
            comentarioExistente.setText(comentarioRequest.getText());
            
            // Guardar el comentario actualizado en la base de datos
            comentarioService.addComment(comentarioExistente);
            
            logger.info("Updated comment with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error while updating comment with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un comentario por su ID.
     * @param id ID del comentario a eliminar.
     * @return Respuesta de éxito.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        try {
            comentarioService.deleteComment(id);
            logger.info("Deleted comment with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error while deleting comment with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
