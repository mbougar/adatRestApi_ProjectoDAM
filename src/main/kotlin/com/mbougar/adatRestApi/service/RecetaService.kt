package com.mbougar.adatRestApi.service

import com.mbougar.adatRestApi.model.Receta
import com.mbougar.adatRestApi.repository.RecetaRepository
import com.mbougar.adatRestApi.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class RecetaService {

    @Autowired
    private lateinit var recetaRepository: RecetaRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    fun createReceta(receta: Receta): Receta {
        val authentication = SecurityContextHolder.getContext().authentication
        val usuarioAutenticado = if (authentication.principal is UserDetails) {
            (authentication.principal as UserDetails).username
        } else {
            authentication.principal.toString()
        }

        val usuario = usuarioRepository.findByUsername(usuarioAutenticado)
        if (!usuario.isPresent) {
            throw IllegalArgumentException("Usuario no encontrado.")
        }

        if (receta.fechaCreacion?.isBefore(java.time.LocalDateTime.now()) == true) {
            throw IllegalArgumentException("La fecha de creación no puede ser anterior a la fecha actual.")
        }

        val isAdmin = usuario.get().roles == "admin"
        val isOwner = receta.usuario?.id == usuario.get().id

        if (!isAdmin && !isOwner) {
            throw IllegalArgumentException("No tiene permisos para crear esta receta.")
        }

        receta.usuario = usuario.get()
        return recetaRepository.save(receta)
    }

    fun getAllRecetas(): List<Receta> {
        return recetaRepository.findAll()
    }

    fun getRecetaById(id: Long): Receta? {
        return recetaRepository.findById(id).orElse(null)
    }

    fun updateReceta(id: Long, receta: Receta): Receta? {
        val existingReceta = recetaRepository.findById(id)
        if (existingReceta.isPresent) {
            val recetaToUpdate = existingReceta.get()
            recetaToUpdate.nombre = receta.nombre ?: recetaToUpdate.nombre
            recetaToUpdate.pasos = receta.pasos ?: recetaToUpdate.pasos
            return recetaRepository.save(recetaToUpdate)
        }
        return null
    }

    fun deleteRecetaById(id: Long): Boolean {
        return if (recetaRepository.existsById(id)) {
            recetaRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}