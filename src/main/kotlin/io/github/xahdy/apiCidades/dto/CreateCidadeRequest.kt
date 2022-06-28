package io.github.xahdy.apiCidades.dto

import io.github.xahdy.apiCidades.core.formatar
import io.github.xahdy.apiCidades.domain.Cidade
import io.github.xahdy.apiCidades.domain.Estado
import javax.validation.constraints.NotBlank

data class CreateCidadeRequest(
    @field:NotBlank
    var nome: String = "",
    var estado: String = ""
) {
    fun create(estado: Estado) = Cidade(
        nome = formatar(nome),
        estado = estado
    )

}