package io.github.xahdy.apiCidades.domain.mapper

interface Mapper<T, U> {

    fun map(t: T): U

}
