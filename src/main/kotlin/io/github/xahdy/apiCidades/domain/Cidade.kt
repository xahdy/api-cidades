package io.github.xahdy.apiCidades.domain

import javax.persistence.*

@Entity
@Table(name = "cidades")
data class Cidade(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,
    var nome: String = "",
    @ManyToOne
    @JoinColumn(name = "estado_id")
    var estado: Estado? = null
)