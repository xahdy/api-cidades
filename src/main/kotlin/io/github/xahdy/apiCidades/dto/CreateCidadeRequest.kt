package io.github.xahdy.apiCidades.dto

import io.github.xahdy.apiCidades.domain.Estado
import javax.validation.constraints.NotBlank

data class CreateCidadeRequest(
    @field:NotBlank
    var nome: String? = null,
    var estadoId: Long? = null
)