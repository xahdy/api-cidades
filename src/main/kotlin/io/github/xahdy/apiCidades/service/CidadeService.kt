package io.github.xahdy.apiCidades.service

import io.github.xahdy.apiCidades.domain.Cidade
import io.github.xahdy.apiCidades.domain.mapper.CidadeMapper
import io.github.xahdy.apiCidades.dto.CidadeResponse
import io.github.xahdy.apiCidades.dto.CreateCidadeRequest
import io.github.xahdy.apiCidades.repository.CidadeRepository
import io.quarkus.panache.common.Parameters
import javax.enterprise.context.Dependent

@Dependent
open class CidadeService(
    private val repository: CidadeRepository,
    private val estadoService: EstadoService,
    private val cidadeResponseMapper: CidadeMapper
) {

    fun listarTodasCidadesEstado(estadoId: Long): List<CidadeResponse> {

        val queryCidadesPorEstado = repository.find("estado_id =:estadoId", paramsEstado(estadoId))
        val listCidadesPorEstado = queryCidadesPorEstado.list()
        return listCidadesPorEstado.map { t -> cidadeResponseMapper.map(t) }
    }

    fun listarCidadeId(estadoId: Long, cidadeId: Long): CidadeResponse {
        val queryCidade = repository.find(
            "estado_id =:estadoId and id =:cidadeId", paramsEstadoCidade(estadoId, cidadeId)
        )
        if(queryCidade.list().isEmpty()){
            throw IllegalArgumentException("Id informado não corresponde a nenhum dado cadastrado")
        }
        val cidadeSelecionada = queryCidade.list()[0]
        return cidadeResponseMapper.map(cidadeSelecionada)
    }

    fun cadastrarCidade(estadoId: Long, createCidadeRequest: CreateCidadeRequest): Cidade {
        val estado = estadoService.listarEstadoId(estadoId)
        val cidade = Cidade()
        cidade.nome = createCidadeRequest.nome
        cidade.estadoId = estado
        repository.persist(cidade)
        return cidade
    }

    fun atualizarCidade(estadoId: Long, cidadeId: Long, createCidadeRequest: CreateCidadeRequest): CidadeResponse {

        val queryCidade = repository.find(
            "estado_id =:estadoId and id =:cidadeId", paramsEstadoCidade(estadoId, cidadeId)
        )
        val listCidade = queryCidade.list()
        val cidadeSelecionada = listCidade[0]
        cidadeSelecionada.nome = createCidadeRequest.nome
        return cidadeResponseMapper.map(cidadeSelecionada)
    }

    fun deletarCidade(estadoId: Long, cidadeId: Long) {
        val cidadeDeletar = repository.find("estado_id =:estadoId and id =:cidadeId", paramsEstadoCidade(estadoId, cidadeId))
        if(cidadeDeletar.list().isEmpty()){
            throw IllegalArgumentException("Id informado não corresponde a nenhum dado cadastrado")
        }
        repository.delete(cidadeDeletar.list()[0])
    }

    fun paramsEstadoCidade(estadoId: Long, cidadeId: Long) =
        Parameters.with("estadoId", estadoId)
            .and("cidadeId", cidadeId)
            .map()

    fun paramsEstado(estadoId: Long) =
        Parameters.with("estadoId", estadoId)
            .map()


}
