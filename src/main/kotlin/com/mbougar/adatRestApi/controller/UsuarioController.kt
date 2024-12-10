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

        if (usuarioService.existsByUsername(newUsuario.username)) {
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)
        }

        val usuarioRegistrado = usuarioService.registerUsuario(newUsuario)

        return ResponseEntity(usuarioRegistrado, HttpStatus.CREATED)
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
}