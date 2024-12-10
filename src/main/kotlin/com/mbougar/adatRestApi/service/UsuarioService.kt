package com.mbougar.adatRestApi.service

import com.mbougar.adatRestApi.model.Usuario
import com.mbougar.adatRestApi.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

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
            .roles(usuario.role)
            .build()
    }

    fun registerUsuario(usuario: Usuario): Usuario {
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