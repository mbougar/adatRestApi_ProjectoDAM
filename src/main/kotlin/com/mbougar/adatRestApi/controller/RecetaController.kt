package com.mbougar.adatRestApi.controller

import com.mbougar.adatRestApi.model.*
import com.mbougar.adatRestApi.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import org.springframework.security.core.Authentication

@RestController
@RequestMapping("/recipes")
class RecetaController {

    @Autowired
    private lateinit var recetaService: RecetaService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @PostMapping
    fun createRecipe(@RequestBody newReceta: Receta, authentication: Authentication): ResponseEntity<Receta> {
        try {
            val recetaCreada = recetaService.createReceta(newReceta)
            return ResponseEntity(recetaCreada, HttpStatus.CREATED)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping
    fun getAllRecipes(): ResponseEntity<List<Receta>> {
        val recetas = recetaService.getAllRecetas()
        return ResponseEntity(recetas, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getRecipeById(@PathVariable id: Long): ResponseEntity<Receta> {
        val receta = recetaService.getRecetaById(id)
        return if (receta != null) {
            ResponseEntity(receta, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateRecipe(
        @PathVariable id: Long,
        @RequestBody receta: Receta
    ): ResponseEntity<Receta> {
        val updatedReceta = recetaService.updateReceta(id, receta)
        return if (updatedReceta != null) {
            ResponseEntity(updatedReceta, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteRecipeById(@PathVariable id: Long): ResponseEntity<Any> {
        return if (recetaService.deleteRecetaById(id)) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(mapOf("mensaje" to "Receta no encontrada"), HttpStatus.NOT_FOUND)
        }
    }
}