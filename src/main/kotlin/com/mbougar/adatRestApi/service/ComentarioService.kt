package com.mbougar.adatRestApi.service

import com.mbougar.adatRestApi.model.Comentario
import com.mbougar.adatRestApi.repository.ComentarioRepository
import com.mbougar.adatRestApi.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class ComentarioService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var comentarioRepository: ComentarioRepository

    fun addComentario(comentario: Comentario): Comentario {
        val authentication = SecurityContextHolder.getContext().authentication
        val usuarioAutenticado = if (authentication.principal is UserDetails) {
            (authentication.principal as UserDetails).username
        } else {
            authentication.principal.toString()
        }

        val usuario = comentario.usuario?.username?.let { usuarioRepository.findByUsername(it) }
        if (usuario != null) {
            if (usuario.isEmpty) {
                throw IllegalArgumentException("Usuario no encontrado.")
            }
        }
        val isAdmin = usuario?.get()?.roles == "admin"
        val isOwner = comentario.usuario?.id == usuario?.get()?.id

        if (!isAdmin && !isOwner) {
            throw IllegalArgumentException("No tiene permisos para añadir este comentario.")
        }

        if (comentario.fechaCreacion?.isBefore(java.time.LocalDateTime.now()) == true) {
            throw IllegalArgumentException("La fecha de creación no puede ser anterior a la fecha actual.")
        }

        if (usuario != null) {
            comentario.usuario = usuario.get()
        }
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