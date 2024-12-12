package com.mbougar.adatRestApi.service

import com.mbougar.adatRestApi.model.Comentario
import com.mbougar.adatRestApi.repository.ComentarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ComentarioService {

    @Autowired
    private lateinit var comentarioRepository: ComentarioRepository

    fun addComentario(comentario: Comentario): Comentario {
        return comentarioRepository.save(comentario)
    }

    fun getComentariosByRecetaId(recipeId: Long): List<Comentario> {
        return comentarioRepository.findComentariosByRecetaId(recipeId)
    }
}