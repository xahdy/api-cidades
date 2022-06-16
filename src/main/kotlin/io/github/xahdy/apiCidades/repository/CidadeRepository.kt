package io.github.xahdy.apiCidades.repository

import io.github.xahdy.apiCidades.domain.Cidade
import io.github.xahdy.apiCidades.domain.Estado
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Parameters
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CidadeRepository : PanacheRepository<Cidade> {

    fun encontrarCidadePorEstadoIdCidadeId(estadoId: Long, cidadeId: Long): Cidade {
        val params = Parameters.with("estadoId", estadoId)
            .and("cidadeId", cidadeId)
            .map()

        val cidadeEncontrada =
            find("estado_id =:estadoId and id =:cidadeId", params)
        if (cidadeEncontrada.list().isEmpty()) {
            throw IllegalArgumentException("Cidade não encontrada")
        }
        return cidadeEncontrada.list()[0]
    }

    fun encontrarTodasCidadesEstado(estadoId: Long): List<Cidade> {
        val params = Parameters.with("estadoId", estadoId)
            .map()
        val queryCidadesPorEstado = find("estado_id =:estadoId", params)
        if (queryCidadesPorEstado.list().isEmpty()) {
            throw IllegalArgumentException("nenhuma cidade cadastrada")
        }
        return queryCidadesPorEstado.list()
    }

    fun encontrarPorCidadeNomeEstadoId(cidadeNome: String, estadoId: Estado?): Cidade? {
        val params = Parameters.with("cidadeNome", cidadeNome)
            .and("estadoId", estadoId)
            .map()
        val queryCidadeNome = find("nome =:cidadeNome and estado_id =:estadoId", params)

        if (queryCidadeNome.firstResult() == null) {
            return queryCidadeNome.firstResult()
        }
        throw RuntimeException("Essa cidade já existe.")

    }
}