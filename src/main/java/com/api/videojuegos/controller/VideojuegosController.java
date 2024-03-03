package com.api.videojuegos.controller;

import com.api.videojuegos.entity.Videojuegos;
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
 * Controlador para las operaciones relacionadas con los videojuegos.
 */
@RestController
@RequestMapping("/api/v1/videojuegos")
public class VideojuegosController {

    private static final Logger logger = LoggerFactory.getLogger(VideojuegosController.class);

    @Autowired
    private VideojuegosService videojuegosService;

    /**
     * Obtiene la lista de todos los videojuegos.
     * @return Lista de todos los videojuegos.
     */
    @GetMapping
    public ResponseEntity<List<Videojuegos>> getAllVideojuegos() {
        try {
            List<Videojuegos> videojuegos = videojuegosService.getAllVideojuegos();
            logger.info("Returned {} videojuegos.", videojuegos.size());
            return new ResponseEntity<>(videojuegos, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting all videojuegos.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Obtiene un videojuego por su ID.
     * @param id ID del videojuego a obtener.
     * @return Videojuego correspondiente al ID proporcionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getVideojuegosById(@PathVariable Long id) {
        try {
            Optional<Videojuegos> optionalVideojuegos = videojuegosService.getVideojuegoById(id);
            if (optionalVideojuegos.isPresent()) {
                Videojuegos videojuegos = optionalVideojuegos.get();
                logger.info("Returned videojuegos with ID: {}", id);
                return new ResponseEntity<>(videojuegos, HttpStatus.OK);
            } else {
                logger.warn("Videojuegos with ID {} not found.", id);
                return new ResponseEntity<>("Videojuegos not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while getting videojuegos with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Crea un nuevo videojuego.
     * @param videojuegos Objeto Videojuegos que contiene la información del nuevo videojuego.
     * @return El videojuego creado.
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createVideojuegos(@RequestBody Videojuegos videojuegos) {
        try {
            Videojuegos createdVideojuegos = videojuegosService.createVideojuego(videojuegos);
            logger.info("Created videojuegos with ID: {}", createdVideojuegos.getId());
            return new ResponseEntity<>(createdVideojuegos, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while creating a new videojuegos.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Actualiza un videojuego existente.
     * @param id ID del videojuego a actualizar.
     * @param videojuegos Objeto Videojuegos con la información actualizada.
     * @return El videojuego actualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateVideojuegos(@PathVariable Long id, @RequestBody Videojuegos videojuegos) {
        try {
            Videojuegos updatedVideojuegos = videojuegosService.updateVideojuego(id, videojuegos);
            if (updatedVideojuegos != null) {
                logger.info("Updated videojuegos with ID: {}", id);
                return new ResponseEntity<>(updatedVideojuegos, HttpStatus.OK);
            } else {
                logger.warn("Videojuegos with ID {} not found for update.", id);
                return new ResponseEntity<>("Videojuegos not found for update", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while updating videojuegos with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Elimina un videojuego por su ID.
     * @param id ID del videojuego a eliminar.
     * @return Respuesta de éxito.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteVideojuegos(@PathVariable Long id) {
        try {
            boolean deleted = videojuegosService.deleteVideojuego(id);
            if (deleted) {
                logger.info("Deleted videojuegos with ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                logger.warn("Videojuegos with ID {} not found for deletion.", id);
                return new ResponseEntity<>("Videojuegos not found for deletion", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while deleting videojuegos with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Busca videojuegos por una palabra clave.
     * @param keyword Palabra clave para buscar videojuegos.
     * @return Lista de videojuegos que coinciden con la palabra clave.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Videojuegos>> searchVideojuegos(@RequestParam String keyword) {
        try {
            List<Videojuegos> searchResults = videojuegosService.searchVideojuegos(keyword);
            logger.info("Returned {} search results for keyword: {}", searchResults.size(), keyword);
            return new ResponseEntity<>(searchResults, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while searching videojuegos.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
