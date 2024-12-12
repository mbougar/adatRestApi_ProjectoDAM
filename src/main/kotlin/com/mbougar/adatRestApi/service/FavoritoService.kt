package com.mbougar.adatRestApi.service

import com.mbougar.adatRestApi.model.Favorito
import com.mbougar.adatRestApi.model.Receta
import com.mbougar.adatRestApi.repository.FavoritoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FavoritoService {

    @Autowired
    private lateinit var favoritoRepository: FavoritoRepository

    fun addFavorito(favorito: Favorito): Favorito {
        return favoritoRepository.save(favorito)
    }

    fun getFavoritosByUsuarioId(usuarioId: Long): List<Receta> {
        return favoritoRepository.findFavoritosByUsuarioId(usuarioId)
    }
}