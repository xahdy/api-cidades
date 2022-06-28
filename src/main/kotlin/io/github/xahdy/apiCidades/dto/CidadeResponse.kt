package io.github.xahdy.apiCidades.dto

import io.github.xahdy.apiCidades.domain.Cidade

data class CidadeResponse(
    var id: Long? = null,
    var nome: String? = null,
    var estado: String? = null
) {
    constructor(cidade: Cidade) : this(
        id = cidade.id,
        nome = cidade.nome,
        estado = cidade.estado?.sigla
    )
    fun todasCidades(cidade: Cidade): Cidade {
        return Cidade (id = cidade.id, nome = cidade.nome)
    }

}