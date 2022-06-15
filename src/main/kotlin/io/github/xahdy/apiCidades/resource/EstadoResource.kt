package io.github.xahdy.apiCidades.resource

import io.github.xahdy.apiCidades.domain.Estado
import io.github.xahdy.apiCidades.dto.CreateEstadoRequest
import io.github.xahdy.apiCidades.service.EstadoService
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class EstadoResource(private val service: EstadoService) {


    @POST
    @Transactional
    fun cadastrar(createEstadoRequest: CreateEstadoRequest): Response {
        val estado = Estado()
        estado.nome = createEstadoRequest.nome
        service.cadastrar(estado)
        return Response.status(Response.Status.CREATED.statusCode).entity(estado).build();
    }

    @GET
    @Path("{estadoId}")
    fun listarPorId(@PathParam("estadoId") estadoId: Long): Response =
        Response.ok(service.listarEstadoId(estadoId)).build()


    @GET
    fun listarTodos(): Response = Response.ok(service.listarTodos().list()).build()

}