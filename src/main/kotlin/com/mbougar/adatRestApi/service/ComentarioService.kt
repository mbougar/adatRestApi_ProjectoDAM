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

    fun getComentarioById(id: Long): Comentario? {
        return comentarioRepository.findById(id).orElse(null)
    }

    fun getAllComentarios(): List<Comentario> {
        return comentarioRepository.findAll()
    }

    fun updateComentario(id: Long, comentario: Comentario): Comentario? {
        val existingComentario = comentarioRepository.findById(id)
        if (existingComentario.isPresent) {
            val comentarioToUpdate = existingComentario.get()
            comentarioToUpdate.comentario = comentario.comentario ?: comentarioToUpdate.comentario
            comentarioToUpdate.fechaCreacion = comentario.fechaCreacion ?: comentarioToUpdate.fechaCreacion
            return comentarioRepository.save(comentarioToUpdate)
        }
        return null
    }

    fun deleteComentarioById(id: Long): Boolean {
        return if (comentarioRepository.existsById(id)) {
            comentarioRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}