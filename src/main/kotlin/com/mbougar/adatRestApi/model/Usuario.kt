package com.mbougar.adatRestApi.model

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var username: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    var role: String? = null, // "user" o "admin"

    var email: String? = null,

    @Lob // Define el campo como un Large Object y me permite mas de 255 caracteres
    var bio: String? = null,

    @Column(name = "fecha_registro")
    var fechaRegistro: java.time.LocalDateTime? = null
)

