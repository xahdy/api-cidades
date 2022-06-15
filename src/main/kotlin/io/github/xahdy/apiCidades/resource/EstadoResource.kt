package io.github.xahdy.apiCidades.resource

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

    @GET
    @Path("{estadoId}")
    fun listarPorId(
        @PathParam("estadoId") estadoId: Long
    ) = Response.ok(service.listarEstadoId(estadoId)).build()

    @GET
    fun listarTodos() = Response.ok(service.listarTodos().list()).build()

    @POST
    @Transactional
    fun cadastrar(
        createEstadoRequest: CreateEstadoRequest
    ) = Response.status(Response.Status.CREATED.statusCode)
        .entity(service.cadastrar(createEstadoRequest)).build();

    @PUT
    @Path("{estadoId}")
    @Transactional
    fun atualizarPorId(
        @PathParam("estadoId") estadoId: Long,
        createEstadoRequest: CreateEstadoRequest
    ) = Response.ok(service.atualizarEstado(estadoId, createEstadoRequest)).build()

    @DELETE
    @Path("{estadoId}")
    @Transactional
    fun deletarPorId(@PathParam("estadoId") estadoId: Long) {
        service.deletar(estadoId)
        Response.noContent().build()
    }

}