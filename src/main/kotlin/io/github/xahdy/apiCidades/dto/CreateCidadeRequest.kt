package io.github.xahdy.apiCidades.dto

import io.github.xahdy.apiCidades.domain.Estado

data class CreateCidadeRequest (
    var nome: String? = null,
    var estadoId: Long? = null
)