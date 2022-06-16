package io.github.xahdy.apiCidades.service


import io.github.xahdy.apiCidades.domain.Estado
import io.github.xahdy.apiCidades.dto.CreateEstadoRequest
import io.github.xahdy.apiCidades.repository.EstadoRepository
import javax.enterprise.context.Dependent


@Dependent
open class EstadoService(
    private val repository: EstadoRepository
) {
    fun cadastrarEstado(createEstadoRequest: CreateEstadoRequest): Estado {

        val estado = Estado()
        estado.nome = createEstadoRequest.nome
        repository.persist(estado)
        return estado
    }

    fun listarEstadoId(estadoId: Long) = repository.findById(estadoId)
    fun listarTodosEstados() = repository.findAll()
    fun atualizarEstado(estadoId: Long, createEstadoRequest: CreateEstadoRequest): Estado? {
        var estadoAtualizado = repository.findById(estadoId)
        estadoAtualizado?.nome = createEstadoRequest.nome
        return estadoAtualizado
    }

    fun deletarEstado(estadoId: Long) {
        repository.deleteById(estadoId)
    }
}