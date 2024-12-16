package com.mbougar.adatRestApi.controller

import com.mbougar.adatRestApi.model.*
import com.mbougar.adatRestApi.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/favorites")
class FavoritoController {

    @Autowired
    private lateinit var favoritoService: FavoritoService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var recetaService: RecetaService

    @PostMapping
    fun addFavorite(@RequestBody favorito: Favorito): ResponseEntity<Favorito> {
        val favoritoCreado = favoritoService.addFavorito(favorito)
        return ResponseEntity(favoritoCreado, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getFavoritesByUserId(@PathVariable id: Long): ResponseEntity<List<Receta>> {
        val favoritos = favoritoService.getFavoritosByUsuarioId(id)
        return ResponseEntity(favoritos, HttpStatus.OK)
    }
}