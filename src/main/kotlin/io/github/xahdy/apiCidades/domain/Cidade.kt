package io.github.xahdy.apiCidades.domain

import javax.persistence.*

@Entity
@Table(name = "cidades")
data class Cidade (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var nome: String? = null,
    @ManyToOne
    @JoinColumn(name = "estado_id")
    var estadoId: Estado? = null
)