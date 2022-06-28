package io.github.xahdy.apiCidades.dto

import io.github.xahdy.apiCidades.core.formatar
import io.github.xahdy.apiCidades.domain.Estado
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern


data class CreateEstadoRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    @field:NotBlank
    @field:Pattern(
        regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]{3,20}",
        message = "O nome do estado deve conter apenas letras. Min: 3, max: 20"
    )
    var nome: String = "",

    @field:NotBlank
    @field:Pattern(
        regexp = "^[A-Za-z]{2,2}",
        message = "O campo sigla deve ser preenchido com duas letras"
    )
    var sigla: String = ""
) {
    fun create() = Estado(
        nome = formatar(nome),
        sigla = formatar(sigla)
    )


}
