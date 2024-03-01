package com.api.videojuegos.config;

import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.repository.UsuarioRepository;
import com.api.videojuegos.repository.VideojuegosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InicializarDatos implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VideojuegosRepository videojuegosRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Usuario 1
            Usuario usuario1 = new Usuario();
            usuario1.setFirstName("Adrián");
            usuario1.setLastName("Tellado");
            usuario1.setEmail("adri@gmail.com");
            usuario1.setPassword(passwordEncoder.encode("password"));
            usuarioRepository.save(usuario1);

            // Usuario 2
            Usuario usuario2 = new Usuario();
            usuario2.setFirstName("Juan");
            usuario2.setLastName("Álvarez");
            usuario2.setEmail("juan@gmail.com");
            usuario2.setPassword(passwordEncoder.encode("password"));
            usuarioRepository.save(usuario2);
            
            // Usuario 3
            Usuario usuario3 = new Usuario();
            usuario3.setFirstName("Pepe");
            usuario3.setLastName("Gómez");
            usuario3.setEmail("pepe@gmail.com");
            usuario3.setPassword(passwordEncoder.encode("password"));
            usuarioRepository.save(usuario3);

            
            // Crear videojuegos
            // Videojuego 1
            Videojuegos videojuegos1 = new Videojuegos();
            videojuegos1.setNombre("Ratchet and Clank");
            videojuegos1.setGenero("Plataformas");
            videojuegosRepository.save(videojuegos1);

            // Videojuego 2
            Videojuegos videojuegos2 = new Videojuegos();
            videojuegos2.setNombre("God od War");
            videojuegos2.setGenero("Acción");
            videojuegosRepository.save(videojuegos2);
            
            // Videojuego 3
            Videojuegos videojuegos3 = new Videojuegos();
            videojuegos3.setNombre("Call of Duty");
            videojuegos3.setGenero("Disparos");
            videojuegosRepository.save(videojuegos3);

        } catch (Exception e) {
        	
        }
    }
}
