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

    @PostMapping("/favorite")
    fun addFavorite(@RequestBody favorito: Favorito): ResponseEntity<Favorito> {

        val usuario = favorito.usuario?.id?.let { usuarioService.getUsuarioById(it) }
        if (usuario == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val receta = favorito.receta?.id?.let { recetaService.getRecetaById(it) }
        if (receta == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        favorito.usuario = usuario
        favorito.receta = receta
        val favoritoCreado = favoritoService.addFavorito(favorito)
        return ResponseEntity(favoritoCreado, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getFavoritesByUserId(@PathVariable id: Long): ResponseEntity<List<Receta>> {
        val favoritos = favoritoService.getFavoritosByUsuarioId(id)
        return ResponseEntity(favoritos, HttpStatus.OK)
    }
}