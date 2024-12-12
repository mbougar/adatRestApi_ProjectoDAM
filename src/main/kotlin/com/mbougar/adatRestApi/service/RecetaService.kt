package com.mbougar.adatRestApi.service

import com.mbougar.adatRestApi.model.Receta
import com.mbougar.adatRestApi.repository.RecetaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RecetaService {

    @Autowired
    private lateinit var recetaRepository: RecetaRepository

    fun createReceta(receta: Receta): Receta {

        val usuario =

        return recetaRepository.save(receta)
    }

    fun getAllRecetas(): List<Receta> {
        return recetaRepository.findAll()
    }

    fun getRecetaById(id: Long): Receta? {
        return recetaRepository.findById(id).orElse(null)
    }
}