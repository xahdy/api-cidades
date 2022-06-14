package io.github.xahdy.apiCidades.service


import io.github.xahdy.apiCidades.domain.Estado
import io.github.xahdy.apiCidades.repository.EstadoRepository

class EstadoService(
    private val repository: EstadoRepository
) {
    fun cadastrar(estado: Estado){
        repository.persist(estado)
    }
}