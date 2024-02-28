package com.api.videojuegos.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.videojuegos.entity.Comentario;
import com.api.videojuegos.repository.ComentarioRepository;
import com.api.videojuegos.service.ComentarioService;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    ComentarioRepository commentRepository;
    
    @Override
    public List<Comentario> getAllComments() {
        return commentRepository.findAll();
    }
    
    @Override
    public Comentario findById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }
    
    @Override
    public void addComment(Comentario comment) {
        commentRepository.save(comment);
    }
    
    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
