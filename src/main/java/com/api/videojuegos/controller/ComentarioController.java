package com.api.videojuegos.controller;

import com.api.videojuegos.entity.Comentario;
import com.api.videojuegos.service.ComentarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controlador para las operaciones relacionadas con los comentarios.
 */

@RestController
@RequestMapping("/api/v1/comentarios")
public class ComentarioController {

    private static final Logger logger = LoggerFactory.getLogger(ComentarioController.class);

    @Autowired
    ComentarioService comentarioService;

    
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
     * @param comentario Objeto Comentario con la información del nuevo comentario.
     * @return Respuesta de éxito.
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> addComentario(@RequestBody Comentario comentario) {
        try {
            comentarioService.addComment(comentario);
            logger.info("Added new comment with ID: {}", comentario.getId());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while adding a new comment.", e);
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
