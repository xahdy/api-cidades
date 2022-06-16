package io.github.xahdy.apiCidades.repository

import io.github.xahdy.apiCidades.domain.Estado
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Parameters
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class EstadoRepository : PanacheRepository<Estado> {

    fun encontrarPorEstadoNome(estadoNome: String): Estado? {
        val params = Parameters.with("estadoNome", estadoNome)
            .map()
        val queryEstadoNome = find("nome =:estadoNome", params)

        if (queryEstadoNome.firstResult() == null) {
            return queryEstadoNome.firstResult()
        }
        throw RuntimeException("Esse estado já existe.")

    }
}