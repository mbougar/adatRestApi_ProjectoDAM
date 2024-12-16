package com.mbougar.adatRestApi.model

import jakarta.persistence.*

@Entity
@Table(name = "recetas")
data class Receta(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var nombre: String? = null,

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    var usuario: Usuario? = null,

    @Column(name = "fecha_creacion")
    var fechaCreacion: java.time.LocalDateTime? = null,

    @Lob
    @Column(nullable = false)
    var pasos: String? = null,
)

