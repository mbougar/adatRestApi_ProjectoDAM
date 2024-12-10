package com.mbougar.adatRestApi.model

import jakarta.persistence.*


@Entity
@Table(name = "favoritos")
data class Favorito(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    var usuario: Usuario? = null,

    @ManyToOne
    @JoinColumn(name = "id_receta", referencedColumnName = "id")
    var receta: Receta? = null,

    @Column(name = "fecha_agregado")
    var fechaAgregado: java.time.LocalDateTime? = null
)

