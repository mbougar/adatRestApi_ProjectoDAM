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

        if (receta.usuario != usuario) {
            throw IllegalArgumentException("El usuario autenticado no coincide con el creador de la receta.")
        }

        return recetaRepository.save(receta)
    }

    fun getAllRecetas(): List<Receta> {
        return recetaRepository.findAll()
    }

    fun getRecetaById(id: Long): Receta? {
        return recetaRepository.findById(id).orElse(null)
    }
}