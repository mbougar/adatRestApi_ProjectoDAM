package com.mbougar.adatRestApi.controller

import com.mbougar.adatRestApi.model.*
import com.mbougar.adatRestApi.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class ComentarioController {

    @Autowired
    private lateinit var comentarioService: ComentarioService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var recetaService: RecetaService

    @PostMapping("/comment")
    fun addComment(@RequestBody comentario: Comentario): ResponseEntity<Comentario> {

        val usuario = comentario.usuario?.id?.let { usuarioService.getUsuarioById(it) }
        if (usuario == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val receta = comentario.receta?.id?.let { recetaService.getRecetaById(it) }
        if (receta == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        comentario.usuario = usuario
        comentario.receta = receta
        val comentarioCreado = comentarioService.addComentario(comentario)
        return ResponseEntity(comentarioCreado, HttpStatus.CREATED)
    }

    @GetMapping("/{recipe_id}")
    fun getCommentsByRecipeId(@PathVariable recipe_id: Long): ResponseEntity<List<Comentario>> {
        val comentarios = comentarioService.getComentariosByRecetaId(recipe_id)
        return ResponseEntity(comentarios, HttpStatus.OK)
    }

    @GetMapping
    fun getAllComments(): ResponseEntity<List<Comentario>> {
        val comentarios = comentarioService.getAllComentarios()
        return ResponseEntity(comentarios, HttpStatus.OK)
    }

    @GetMapping("/comment/{id}")
    fun getCommentById(@PathVariable id: Long): ResponseEntity<Comentario> {
        val comentario = comentarioService.getComentarioById(id)
        return if (comentario != null) {
            ResponseEntity(comentario, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/comment/{id}")
    fun updateComment(
        @PathVariable id: Long,
        @RequestBody comentario: Comentario
    ): ResponseEntity<Comentario> {
        val updatedComentario = comentarioService.updateComentario(id, comentario)
        return if (updatedComentario != null) {
            ResponseEntity(updatedComentario, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/comment/{id}")
    fun deleteCommentById(@PathVariable id: Long): ResponseEntity<Any> {
        return if (comentarioService.deleteComentarioById(id)) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(mapOf("mensaje" to "Comentario no encontrado"), HttpStatus.NOT_FOUND)
        }
    }
}