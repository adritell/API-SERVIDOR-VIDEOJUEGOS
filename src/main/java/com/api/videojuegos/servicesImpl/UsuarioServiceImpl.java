package com.api.videojuegos.servicesImpl;

import com.api.videojuegos.dto.UsuarioResponse;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.repository.UsuarioRepository;
import com.api.videojuegos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public Usuario createUser(Usuario user) {
        return userRepository.save(user);
    }

    @Override
    public List<UsuarioResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UsuarioResponse(user.getFirstName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse findUserById(Long id) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UsuarioResponse(user.getFirstName(), user.getEmail());
    }

    
    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Usuario updateUser(Long id, Usuario user) {
        // Verificar si el usuario con el ID proporcionado existe
        Optional<Usuario> optionalUsuario = userRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            // Actualizar los detalles del usuario
            Usuario usuarioExistente = optionalUsuario.get();
            usuarioExistente.setFirstName(user.getFirstName());
            usuarioExistente.setEmail(user.getEmail());
            
            return userRepository.save(usuarioExistente);
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
    }
    
    @Override
    public Optional<Usuario> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    @Override
    public UserDetailsService userDetailsService() {
        return (UserDetailsService) username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
