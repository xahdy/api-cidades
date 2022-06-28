package io.github.xahdy.apiCidades.domain.mapper

import io.github.xahdy.apiCidades.domain.Cidade
import io.github.xahdy.apiCidades.dto.CidadeResponse
import javax.enterprise.context.Dependent

@Dependent
open class CidadeMapper : Mapper<Cidade, CidadeResponse> {
    override fun map(t: Cidade) =
        CidadeResponse(
            id = t.id,
            nome = t.nome
        )

}