package io.github.xahdy.apiCidades.repository

import io.github.xahdy.apiCidades.domain.Estado
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.NotFoundException

@ApplicationScoped
class EstadoRepository : PanacheRepository<Estado> {

    fun countDuplicados(estadoNome: String, estadoSigla: String) {
        val contadorNome = count(query = "nome", estadoNome).toInt()
        val contadorSigla = count(query = "sigla", estadoSigla).toInt()
        if (contadorNome != 0 || contadorSigla != 0) {
            throw IllegalArgumentException("Duplicado")
        }
    }

    fun cidadeEncontrarEstado(estado: String): Estado {
        val buscaPor = when (estado.length) {
            2 -> "sigla"
            else -> {
                "nome"
            }
        }

        val estadoRetorno = find("LOWER($buscaPor)", estado.lowercase()).firstResult()
        if (estadoRetorno != null) {
            return estadoRetorno
        }
        throw IllegalArgumentException("não encontrado")
    }
}