package com.mbougar.adatRestApi.service

import com.mbougar.adatRestApi.model.Favorito
import com.mbougar.adatRestApi.model.Receta
import com.mbougar.adatRestApi.repository.FavoritoRepository
import com.mbougar.adatRestApi.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class FavoritoService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var favoritoRepository: FavoritoRepository

    fun addFavorito(favorito: Favorito): Favorito {
        if (favorito.fechaAgregado?.isBefore(java.time.LocalDateTime.now()) == true) {
            throw IllegalArgumentException("La fecha de agregado no puede ser anterior a la fecha actual.")
        }

        val authentication = SecurityContextHolder.getContext().authentication
        val usuarioAutenticado = if (authentication.principal is UserDetails) {
            (authentication.principal as UserDetails).username
        } else {
            authentication.principal.toString()
        }

        val usuario = favorito.usuario?.id?.let { usuarioRepository.findById(it) }
        if (usuario == null || !usuario.isPresent) {
            throw IllegalArgumentException("Usuario no encontrado.")
        }

        val isAdmin = usuario.get().roles == "admin"
        val isOwner = favorito.usuario?.id == usuario.get().id

        if (!isAdmin && !isOwner) {
            throw IllegalArgumentException("No tiene permisos para agregar este favorito.")
        }

        favorito.usuario = usuario.get()
        return favoritoRepository.save(favorito)
    }

    fun getFavoritosByUsuarioId(usuarioId: Long): List<Receta> {
        return favoritoRepository.findFavoritosByUsuarioId(usuarioId)
    }
}