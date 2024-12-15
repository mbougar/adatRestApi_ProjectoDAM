package com.mbougar.adatRestApi.service

import com.mbougar.adatRestApi.model.Usuario
import com.mbougar.adatRestApi.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class UsuarioService: UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario: Usuario = usuarioRepository
            .findByUsername(username!!)
            .orElseThrow { Exception("Usuario no encontrado") }

        return User
            .builder()
            .username(usuario.username)
            .password(usuario.password)
            .roles(usuario.roles)
            .build()
    }

    fun registerUsuario(usuario: Usuario): Usuario {
        val fechaActual = LocalDateTime.now(ZoneId.systemDefault())
        if (usuario.fechaRegistro != null && usuario.fechaRegistro!!.isBefore(fechaActual)) {
            throw Exception("La fecha de creación no puede ser anterior a la actual.")
        }

        val existingEmail = usuarioRepository.findByEmail(usuario.email!!)
        if (existingEmail.isPresent) {
            throw Exception("El correo ya está registrado.")
        }

        val existingUsuario = usuarioRepository.findByUsername(usuario.username!!)
        if (existingUsuario.isPresent) {
            throw Exception("El usuario ya existe")
        }

        usuario.password = passwordEncoder.encode(usuario.password)

        return usuarioRepository.save(usuario)
    }

    fun existsByUsername(username: String?): Boolean {
        val existingUsuario = usuarioRepository.findByUsername(username!!)
        return existingUsuario.isPresent
    }

    fun getUsuarioById(id: Long): Usuario? {
        return usuarioRepository.findById(id).orElse(null)
    }
}