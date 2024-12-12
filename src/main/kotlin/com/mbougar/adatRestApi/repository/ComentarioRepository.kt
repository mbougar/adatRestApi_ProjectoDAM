package com.mbougar.adatRestApi.repository

import com.mbougar.adatRestApi.model.Comentario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ComentarioRepository : JpaRepository<Comentario, Long> {
    @Query("SELECT c FROM Comentario c WHERE c.receta.id = :recipeId")
    fun findComentariosByRecetaId(@Param("recipeId") recipeId: Long): List<Comentario>
}