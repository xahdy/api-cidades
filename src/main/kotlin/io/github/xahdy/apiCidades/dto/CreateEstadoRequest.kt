package io.github.xahdy.apiCidades.dto

import javax.validation.constraints.NotBlank


data class CreateEstadoRequest(
    @field:NotBlank
    var nome: String? = null
)