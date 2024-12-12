package com.mbougar.adatRestApi.repository

import com.mbougar.adatRestApi.model.Favorito
import com.mbougar.adatRestApi.model.Receta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FavoritoRepository : JpaRepository<Favorito, Long> {
    @Query("SELECT f.receta FROM Favorito f WHERE f.usuario.id = :usuarioId")
    fun findFavoritosByUsuarioId(@Param("usuarioId") usuarioId: Long): List<Receta>
}