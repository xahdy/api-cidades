package io.github.xahdy.apiCidades.service

import io.github.xahdy.apiCidades.domain.Cidade
import io.github.xahdy.apiCidades.domain.mapper.CidadeMapper
import io.github.xahdy.apiCidades.dto.CidadeResponse
import io.github.xahdy.apiCidades.dto.CreateCidadeRequest
import io.github.xahdy.apiCidades.repository.CidadeRepository
import javax.enterprise.context.Dependent

@Dependent
open class CidadeService(
    private val repository: CidadeRepository,
    private val estadoService: EstadoService,
    private val cidadeResponseMapper: CidadeMapper
) {

    fun listarTodasCidadesEstado(estadoId: Long): List<CidadeResponse> {
        estadoService.listarEstadoId(estadoId)

        val listCidadesPorEstado = repository.encontrarTodasCidadesEstado(estadoId)
        return listCidadesPorEstado.map { t -> cidadeResponseMapper.map(t) }
    }

    fun listarCidadeId(estadoId: Long, cidadeId: Long): CidadeResponse {
        val cidadeListar = repository.encontrarCidadePorEstadoIdCidadeId(estadoId, cidadeId)
        return cidadeResponseMapper.map(cidadeListar)
    }

    fun cadastrarCidade(estadoId: Long, createCidadeRequest: CreateCidadeRequest): Cidade {
        val estado = estadoService.listarEstadoId(estadoId)

        val cidade = Cidade()
        cidade.nome = createCidadeRequest.nome
        cidade.estadoId = estado
        val cidadeNome = cidade.nome
        val cidadeEstadoId = cidade.estadoId
        if (cidadeNome != null && cidadeEstadoId != null) {
            repository.encontrarPorCidadeNomeEstadoId(cidadeNome, cidadeEstadoId)
        }
        repository.persist(cidade)
        return cidade
    }

    fun atualizarCidade(estadoId: Long, cidadeId: Long, createCidadeRequest: CreateCidadeRequest): CidadeResponse {

        val cidadeAtualizar = repository.encontrarCidadePorEstadoIdCidadeId(estadoId, cidadeId)

        val cidadeNome = createCidadeRequest.nome
        val cidadeEstadoId = cidadeAtualizar.estadoId
        if (cidadeNome != null && cidadeEstadoId != null) {
            repository.encontrarPorCidadeNomeEstadoId(cidadeNome, cidadeEstadoId)
        }

        cidadeAtualizar.nome = createCidadeRequest.nome
        return cidadeResponseMapper.map(cidadeAtualizar)
    }

    fun deletarCidade(estadoId: Long, cidadeId: Long) {
        val cidadeDeletar = repository.encontrarCidadePorEstadoIdCidadeId(estadoId, cidadeId)
        repository.delete(cidadeDeletar)
    }


}
