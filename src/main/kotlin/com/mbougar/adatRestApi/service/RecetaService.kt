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
        if (usuario.isPresent && receta.usuario == usuario.get()) {
            return recetaRepository.save(receta)
        } else {
            throw IllegalArgumentException("El usuario autenticado no coincide con el creador de la receta.")
        }
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
            recetaToUpdate.imagen = receta.imagen ?: recetaToUpdate.imagen
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