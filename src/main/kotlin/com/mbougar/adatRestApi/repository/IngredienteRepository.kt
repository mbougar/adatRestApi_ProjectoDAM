package com.mbougar.adatRestApi.repository

import com.mbougar.adatRestApi.model.Ingrediente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IngredienteRepository : JpaRepository<Ingrediente, Long> {

}