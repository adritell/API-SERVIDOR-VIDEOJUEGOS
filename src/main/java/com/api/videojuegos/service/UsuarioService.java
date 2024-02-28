package com.api.videojuegos.service;

import com.api.videojuegos.dto.UsuarioResponse;
import com.api.videojuegos.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService {

    Usuario createUser(Usuario user);

    UserDetailsService userDetailsService();

    List<UsuarioResponse> getAllUsers();

    UsuarioResponse findUserById(Long id);
}
