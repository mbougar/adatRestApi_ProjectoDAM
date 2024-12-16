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

    fun getUsuarioByUsername(username: String?): Usuario? {
        return username?.let { usuarioRepository.findByUsername(it).orElse(null) }
    }

    fun getUsuarioById(id: Long): Usuario? {
        return usuarioRepository.findById(id).orElse(null)
    }

    fun getAllUsuarios(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun deleteUsuarioById(id: Long): Boolean {
        return if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun updateUsuario(id: Long, usuario: Usuario): Usuario? {
        val existingUsuario = usuarioRepository.findById(id)
        if (existingUsuario.isPresent) {
            val usuarioToUpdate = existingUsuario.get()
            usuarioToUpdate.username = usuario.username ?: usuarioToUpdate.username
            usuarioToUpdate.email = usuario.email ?: usuarioToUpdate.email
            if (!usuario.password.isNullOrBlank()) {
                usuarioToUpdate.password = passwordEncoder.encode(usuario.password)
            }
            usuarioToUpdate.bio = usuario.bio ?: usuarioToUpdate.bio
            usuarioToUpdate.fechaRegistro = usuario.fechaRegistro ?: usuarioToUpdate.fechaRegistro
            usuarioToUpdate.roles = usuario.roles ?: usuarioToUpdate.roles
            return usuarioRepository.save(usuarioToUpdate)
        }
        return null
    }
}