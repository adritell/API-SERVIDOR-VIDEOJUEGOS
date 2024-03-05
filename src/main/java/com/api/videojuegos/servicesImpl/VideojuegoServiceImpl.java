package com.api.videojuegos.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.repository.VideojuegosRepository;
import com.api.videojuegos.service.VideojuegosService;

@Service
public class VideojuegoServiceImpl implements VideojuegosService {

    @Autowired
    private VideojuegosRepository videojuegosRepository;

    @Override
    public List<Videojuegos> getAllVideojuegos() {
        return videojuegosRepository.findAll();
    }

    @Override
    public Optional<Videojuegos> getVideojuegoById(Long id) {
        return videojuegosRepository.findById(id);
    }

    @Override
    public Videojuegos createVideojuego(Videojuegos videojuego) {
        return videojuegosRepository.save(videojuego);
    }

    @Override
    public Videojuegos updateVideojuego(Long id, Videojuegos videojuego) {
        if (videojuegosRepository.existsById(id)) {
            videojuego.setId(id);
            return videojuegosRepository.save(videojuego);
        } else {
            return null;
        }
    }
    
    @Override
    public Optional<Videojuegos> findByNombre(String nombre) {
        return videojuegosRepository.findByNombre(nombre);
    }

    @Override
    public boolean deleteVideojuego(Long id) {
        if (videojuegosRepository.existsById(id)) {
            videojuegosRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Videojuegos> searchVideojuegos(String passwd) {
        return videojuegosRepository.findByNombreContainingIgnoreCase(passwd);
    }
}
