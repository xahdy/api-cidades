package io.github.xahdy.apiCidades.resource

import io.github.xahdy.apiCidades.domain.Estado
import io.github.xahdy.apiCidades.service.EstadoService
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class EstadoResource(private val service: EstadoService) {


    @POST
    fun cadastrar(estado: Estado){
        return service.cadastrar(estado)
    }
}