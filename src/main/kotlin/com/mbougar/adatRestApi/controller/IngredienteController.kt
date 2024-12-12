package com.mbougar.adatRestApi.controller

import com.mbougar.adatRestApi.model.*
import com.mbougar.adatRestApi.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ingredients")
class IngredienteController {

    @Autowired
    private lateinit var ingredienteService: IngredienteService

    @GetMapping
    fun getAllIngredients(): ResponseEntity<List<Ingrediente>> {
        val ingredientes = ingredienteService.getAllIngredientes()
        return ResponseEntity(ingredientes, HttpStatus.OK)
    }
}