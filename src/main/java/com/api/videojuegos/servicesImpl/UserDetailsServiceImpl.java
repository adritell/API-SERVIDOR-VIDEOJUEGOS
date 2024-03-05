package com.api.videojuegos.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // Convertir el conjunto de roles a una lista de nombres de roles
        List<String> roles = user.getRoles().stream()
                                           .map(Enum::name) // Obtener el nombre de cada rol
                                           .collect(Collectors.toList());

        // Devolver un UserDetails basado en la entidad Usuario
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(roles.toArray(new String[0])) // Pasar la lista de roles como un array de strings
                .build();
    }
    
}
