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
    ) = try {
        val estado = service.listarEstadoId(estadoId)
        Response.ok(estado).build()
    } catch (e: NotFoundException) {
        Response.status(Response.Status.NOT_FOUND.statusCode).entity(e).build()
    }

    @GET
    fun listarTodos() = try {
        val estados = service.listarTodosEstados()
        Response.ok(estados).build()
    } catch (e: NotFoundException) {
        Response.status(Response.Status.NOT_FOUND.statusCode).entity(e).build()
    }

    @POST
    @Transactional
    fun cadastrarEstado(
        @Valid createEstadoRequest: CreateEstadoRequest
    ) = try {
        Response.status(Response.Status.CREATED.statusCode)
            .entity(service.cadastrarEstado(createEstadoRequest)).build()
    } catch (e: RuntimeException) {
        Response.status(Response.Status.BAD_REQUEST.statusCode).entity(e).build()
    }

    @PUT
    @Path("{estadoId}")
    @Transactional
    fun atualizarEstadoPorId(
        @PathParam("estadoId") estadoId: Long,
        @Valid createEstadoRequest: CreateEstadoRequest
    ) =
        try {
            Response.ok(service.atualizarEstado(estadoId, createEstadoRequest)).build()
        } catch (e: RuntimeException) {
            Response.status(Response.Status.NOT_FOUND.statusCode).entity(e).build()
        } catch (e: NotFoundException) {
            Response.status(Response.Status.NOT_FOUND.statusCode).entity(e).build()
        }

    @DELETE
    @Path("{estadoId}")
    @Transactional
    fun deletarEstadoPorId(
        @PathParam("estadoId") estadoId: Long
    ) =
        try {
            service.deletarEstado(estadoId)
            Response.noContent().build()
        } catch (e: NotFoundException) {
            Response.status(Response.Status.NOT_FOUND.statusCode).entity(e).build()
        }
}