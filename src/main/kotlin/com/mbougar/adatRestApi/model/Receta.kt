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

    @Lob
    @Column(name = "imagen", nullable = true)
    var imagen: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Receta

        if (id != other.id) return false
        if (nombre != other.nombre) return false
        if (usuario != other.usuario) return false
        if (fechaCreacion != other.fechaCreacion) return false
        if (pasos != other.pasos) return false
        if (imagen != null) {
            if (other.imagen == null) return false
            if (!imagen.contentEquals(other.imagen)) return false
        } else if (other.imagen != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (nombre?.hashCode() ?: 0)
        result = 31 * result + (usuario?.hashCode() ?: 0)
        result = 31 * result + (fechaCreacion?.hashCode() ?: 0)
        result = 31 * result + (pasos?.hashCode() ?: 0)
        result = 31 * result + (imagen?.contentHashCode() ?: 0)
        return result
    }
}

