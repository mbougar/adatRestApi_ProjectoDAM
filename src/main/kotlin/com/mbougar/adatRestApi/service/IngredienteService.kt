package com.mbougar.adatRestApi.service

import com.mbougar.adatRestApi.model.Ingrediente
import com.mbougar.adatRestApi.repository.IngredienteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class IngredienteService {

    @Autowired
    private lateinit var ingredienteRepository: IngredienteRepository

    fun getAllIngredientes(): List<Ingrediente> {
        return ingredienteRepository.findAll()
    }
}