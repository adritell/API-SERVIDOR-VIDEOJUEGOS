package com.api.videojuegos;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.api.videojuegos.controller.VideojuegosController;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.service.VideojuegosService;

@ExtendWith(MockitoExtension.class)
class VideojuegosControllerTest {

    @Mock
    private VideojuegosService videojuegosService;

    @InjectMocks
    private VideojuegosController videojuegosController;

    @Test
    void getVideojuegosById_shouldReturnVideojuegos() {
        // Arrange
        long videojuegosId = 1L;
        Videojuegos mockVideojuegos = new Videojuegos();
        when(videojuegosService.getVideojuegoById(videojuegosId)).thenReturn(Optional.of(mockVideojuegos));

        // Act
        ResponseEntity<?> response = videojuegosController.getVideojuegosById(videojuegosId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockVideojuegos, response.getBody());
    }

    @Test
    void updateVideojuegos_shouldReturnUpdatedVideojuegos() {
        // Arrange
        long videojuegosId = 1L;
        Videojuegos mockVideojuegos = new Videojuegos();
        when(videojuegosService.updateVideojuego(eq(videojuegosId), any(Videojuegos.class))).thenReturn(mockVideojuegos);

        // Act
        ResponseEntity<?> response = videojuegosController.updateVideojuegos(videojuegosId, mockVideojuegos);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockVideojuegos, response.getBody());
    }
}
