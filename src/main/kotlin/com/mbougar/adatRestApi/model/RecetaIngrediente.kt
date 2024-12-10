package com.mbougar.adatRestApi.model

import jakarta.persistence.*

@Entity
@Table(name = "recetas_ingredientes")
data class RecetaIngrediente(

    @Id
    @ManyToOne
    @JoinColumn(name = "id_receta", referencedColumnName = "id")
    var receta: Receta? = null,

    @Id
    @ManyToOne
    @JoinColumn(name = "id_ingrediente", referencedColumnName = "id")
    var ingrediente: Ingrediente? = null,

    var cantidad: String? = null
)

