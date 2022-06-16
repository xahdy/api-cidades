package io.github.xahdy.apiCidades.resource

import io.github.xahdy.apiCidades.dto.CreateEstadoRequest
import io.github.xahdy.apiCidades.service.EstadoService
import javax.transaction.Transactional
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class EstadoResource(private val service: EstadoService) {

    @GET
    @Path("{estadoId}")
    fun listarEstadoPorId(
        @PathParam("estadoId") estadoId: Long
    ) = Response.ok(service.listarEstadoId(estadoId)).build()

    @GET
    fun listarTodos() = Response.ok(service.listarTodosEstados().list()).build()

    @POST
    @Transactional
    fun cadastrarEstado(
        @Valid createEstadoRequest: CreateEstadoRequest
    ) = Response.status(Response.Status.CREATED.statusCode)
        .entity(service.cadastrarEstado(createEstadoRequest)).build();

    @PUT
    @Path("{estadoId}")
    @Transactional
    fun atualizarEstadoPorId(
        @PathParam("estadoId") estadoId: Long,
        @Valid createEstadoRequest: CreateEstadoRequest
    ) = Response.ok(service.atualizarEstado(estadoId, createEstadoRequest)).build()

    @DELETE
    @Path("{estadoId}")
    @Transactional
    fun deletarEstadoPorId(@PathParam("estadoId") estadoId: Long) {
        service.deletarEstado(estadoId)
        Response.noContent().build()
    }

}