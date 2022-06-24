package io.github.xahdy.apiCidades.repository

import io.github.xahdy.apiCidades.core.get
import io.github.xahdy.apiCidades.domain.Estado
import io.github.xahdy.apiCidades.dto.CreateEstadoRequest
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Parameters
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.NotFoundException
import javax.ws.rs.core.Response

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

    fun countDuplicados(estadoNome: String, estadoSigla: String) {
        val contadorNome = count(query ="nome", estadoNome).toInt()
        val contadorSigla = count(query ="sigla", estadoSigla).toInt()
        if (contadorNome != 0 || contadorSigla != 0 ) {
            throw IllegalArgumentException("Duplicado")
        }
    }

}