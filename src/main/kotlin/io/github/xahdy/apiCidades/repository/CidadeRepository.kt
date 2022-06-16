package io.github.xahdy.apiCidades.repository

import io.github.xahdy.apiCidades.domain.Cidade
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CidadeRepository : PanacheRepository<Cidade>{
}