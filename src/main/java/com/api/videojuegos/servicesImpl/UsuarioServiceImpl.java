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
    public UserDetailsService userDetailsService() {
        return (UserDetailsService) username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
