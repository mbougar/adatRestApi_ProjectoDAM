package com.mbougar.adatRestApi.model

import jakarta.persistence.*

@Entity
@Table(name = "comentarios")
data class Comentario(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "id_receta", referencedColumnName = "id")
    var receta: Receta? = null,

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    var usuario: Usuario? = null,

    @Column(nullable = false)
    var comentario: String? = null,

    @Column(name = "fecha_creacion")
    var fechaCreacion: java.time.LocalDateTime? = null
)