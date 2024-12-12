package com.mbougar.adatRestApi.controller

import com.mbougar.adatRestApi.model.*
import com.mbougar.adatRestApi.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/recipes")
class RecetaController {

    @Autowired
    private lateinit var recetaService: RecetaService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @PostMapping
    fun createRecipe(@RequestBody newReceta: Receta): ResponseEntity<Receta> {
        val usuario = newReceta.usuario?.id?.let { usuarioService.getUsuarioById(it) }
        if (usuario != null) {
            newReceta.usuario = usuario
            val recetaCreada = recetaService.createReceta(newReceta)
            return ResponseEntity(recetaCreada, HttpStatus.CREATED)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
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
}