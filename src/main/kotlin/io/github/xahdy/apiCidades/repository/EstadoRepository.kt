package io.github.xahdy.apiCidades.repository

import io.github.xahdy.apiCidades.domain.Estado
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
interface EstadoRepository: PanacheRepository<Estado> {
}