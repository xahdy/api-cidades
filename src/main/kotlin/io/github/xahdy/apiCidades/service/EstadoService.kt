package io.github.xahdy.apiCidades.service


import io.github.xahdy.apiCidades.domain.Estado
import io.github.xahdy.apiCidades.repository.EstadoRepository
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import javax.enterprise.context.Dependent


@Dependent
open class EstadoService(
    private val repository: EstadoRepository
) {
    fun cadastrar(estado: Estado) = repository.persist(estado)
    fun listarEstadoId(estadoId: Long) = repository.findById(estadoId)
    fun listarTodos() = repository.findAll()

}