package com.api.videojuegos.repository;

import com.api.videojuegos.entity.Videojuegos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VideojuegosRepository extends JpaRepository<Videojuegos, Long> {
    List<Videojuegos> findByKeywordIgnoreCase(String passwd);
}
