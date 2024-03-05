package com.api.videojuegos.repository;

import com.api.videojuegos.entity.Videojuegos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideojuegosRepository extends JpaRepository<Videojuegos, Long> {
    List<Videojuegos> findByNombreContainingIgnoreCase(String nombre);
    Optional<Videojuegos> findByNombre(String nombre);
}
