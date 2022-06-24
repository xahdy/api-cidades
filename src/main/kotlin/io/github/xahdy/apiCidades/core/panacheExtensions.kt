package io.github.xahdy.apiCidades.core

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import io.quarkus.panache.common.Page
import javax.ws.rs.NotFoundException

operator fun <T : Any> PanacheRepository<T>.get(entityId: Long) =
    findById(entityId) ?: throw NotFoundException()

operator fun <T : Any> PanacheRepository<T>.get(page: Page, campo: String, busca: String): List<T> {
    val buscaFormatada = busca.trim().split("\\s+".toRegex())
        .joinToString(" ")

    val qtdPag = find("LOWER($campo) LIKE LOWER('%${buscaFormatada}%')").page(page).pageCount() - 1

    if (page.index > qtdPag) {
        throw IllegalArgumentException()
    }

    return find("LOWER($campo) LIKE LOWER('%${buscaFormatada}%')").page(page).list()
}
