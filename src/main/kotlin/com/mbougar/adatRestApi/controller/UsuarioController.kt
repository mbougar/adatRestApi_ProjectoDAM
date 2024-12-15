package com.mbougar.adatRestApi.controller

import com.mbougar.adatRestApi.model.Usuario
import com.mbougar.adatRestApi.service.TokenService
import com.mbougar.adatRestApi.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsuarioController {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenService: TokenService

    @PostMapping("/register")
    fun register(
        @RequestBody newUsuario: Usuario
    ): ResponseEntity<Usuario> {

        return try {
            val usuarioRegistrado = usuarioService.registerUsuario(newUsuario)
            ResponseEntity(usuarioRegistrado, HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody usuario: Usuario): ResponseEntity<Any>? {

        val authentication: Authentication

        try {
            authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(usuario.username, usuario.password)
            )
        } catch (e: AuthenticationException) {
            return ResponseEntity(mapOf("mensaje" to "Credenciales incorrectas"), HttpStatus.UNAUTHORIZED)
        }

        val token = tokenService.generarToken(authentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getUsuarioById(@PathVariable id: Long): ResponseEntity<Usuario>? {
        val usuario = usuarioService.getUsuarioById(id)
        return if (usuario != null) {
            ResponseEntity(usuario, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping
    fun getAllUsuarios(): ResponseEntity<List<Usuario>> {
        val usuarios = usuarioService.getAllUsuarios()
        return ResponseEntity(usuarios, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteUsuarioById(@PathVariable id: Long): ResponseEntity<Any> {
        return if (usuarioService.deleteUsuarioById(id)) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(mapOf("mensaje" to "Usuario no encontrado"), HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateUsuario(
        @PathVariable id: Long,
        @RequestBody usuario: Usuario
    ): ResponseEntity<Usuario> {
        val updatedUsuario = usuarioService.updateUsuario(id, usuario)
        return if (updatedUsuario != null) {
            ResponseEntity(updatedUsuario, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}