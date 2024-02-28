package com.api.videojuegos.service;

import com.api.videojuegos.entity.Comentario;
import java.util.List;

public interface ComentarioService {
    
    List<Comentario> getAllComments();
    
    Comentario findById(Long id);
    
    void addComment(Comentario comment);
    
    void deleteComment(Long id);
}
