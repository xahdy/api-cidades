package io.github.xahdy.apiCidades.repository

import io.github.xahdy.apiCidades.domain.Cidade
import io.github.xahdy.apiCidades.domain.Estado
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Parameters
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CidadeRepository : PanacheRepository<Cidade> {

    fun countDuplicados(cidadeNome: String, estado: Estado) {
        val params = Parameters.with("cidadeNome", cidadeNome.lowercase())
            .and("estado", estado)
            .map()
        val contador = count(query = "LOWER(nome) =:cidadeNome and LOWER (estado) =:estado", params = params).toInt()
        if (contador != 0 ) {
            throw IllegalArgumentException("Duplicado")
        }
    }
}