package io.github.xahdy.apiCidades.service


import io.github.xahdy.apiCidades.domain.Estado
import io.github.xahdy.apiCidades.dto.CreateEstadoRequest
import io.github.xahdy.apiCidades.repository.EstadoRepository
import javax.enterprise.context.Dependent
import javax.ws.rs.NotFoundException


@Dependent
open class EstadoService(
    private val repository: EstadoRepository
) {
    fun cadastrarEstado(createEstadoRequest: CreateEstadoRequest): Estado {

        val estado = Estado()
        estado.nome = createEstadoRequest.nome
        estado.nome?.let { repository.encontrarPorEstadoNome(it) }
        repository.persist(estado)
        return estado
    }

    fun listarEstadoId(estadoId: Long) = encontrarEstado(estadoId)
    fun listarTodosEstados(): List<Estado> {
        val estados = repository.findAll()
        val listEstados = estados.list()
        if (listEstados.isEmpty()) {
            throw NotFoundException("Estados não encontrados")
        }
        return listEstados
    }

    fun atualizarEstado(estadoId: Long, createEstadoRequest: CreateEstadoRequest): Estado? {
        val estadoAtualizado = encontrarEstado(estadoId)

        createEstadoRequest.nome?.let { repository.encontrarPorEstadoNome(it) }

        estadoAtualizado.nome = createEstadoRequest.nome
        return estadoAtualizado
    }

    fun deletarEstado(estadoId: Long) {
        encontrarEstado(estadoId)
        repository.deleteById(estadoId)
    }

    fun encontrarEstado(estadoId: Long) = repository.findById(estadoId)
        ?: throw NotFoundException("Estado não encontrado")

}