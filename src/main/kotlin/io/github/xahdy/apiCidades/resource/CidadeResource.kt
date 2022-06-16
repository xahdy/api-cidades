package io.github.xahdy.apiCidades.resource

import io.github.xahdy.apiCidades.dto.CreateCidadeRequest
import io.github.xahdy.apiCidades.service.CidadeService
import javax.transaction.Transactional
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/estados/{estadoId}/cidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CidadeResource(private val service: CidadeService) {

    @GET
    fun listarTodasCidadesPorEstado(@PathParam("estadoId") estadoId: Long) =
        try {
            val todasCidades = service.listarTodasCidadesEstado(estadoId)
            Response.ok(todasCidades).build()
        } catch (e: NotFoundException) {
            Response.status(Response.Status.NOT_FOUND.statusCode).entity(e).build()
        } catch (e: IllegalArgumentException) {
            Response.status(Response.Status.NOT_FOUND.statusCode).entity(e).entity(e).build()
        }

    @GET
    @Path("{cidadeId}")
    fun listarCidadePorId(
        @PathParam("estadoId") estadoId: Long,
        @PathParam("cidadeId") cidadeId: Long
    ) = try {
        val cidade = service.listarCidadeId(estadoId, cidadeId)
        Response.ok(cidade).build()
    } catch (e: IllegalArgumentException) {
        Response.status(Response.Status.NOT_FOUND.statusCode).entity(e).build()
    }


    @POST
    @Transactional
    fun cadastrarCidade(
        @PathParam("estadoId") estadoId: Long,
        @Valid createCidadeRequest: CreateCidadeRequest
    ) = try {
        val cidade = service.cadastrarCidade(estadoId, createCidadeRequest)
        Response.status(Response.Status.CREATED.statusCode)
            .entity(cidade).build()
    } catch (e: NotFoundException) {
        Response.status(Response.Status.NOT_FOUND.statusCode).entity(e).entity(e).build()
    } catch (e: RuntimeException) {
        Response.status(Response.Status.BAD_REQUEST.statusCode).entity(e).build()
    }


    @PUT
    @Path("{cidadeId}")
    @Transactional
    fun atualizarEstadoPorId(
        @PathParam("estadoId") estadoId: Long,
        @PathParam("cidadeId") cidadeId: Long,
        @Valid createCidadeRequest: CreateCidadeRequest
    ) = try {
        val cidade = service.atualizarCidade(estadoId, cidadeId, createCidadeRequest)
        Response.ok(cidade).build()
    } catch (e: IllegalArgumentException) {
        Response.status(Response.Status.NOT_FOUND.statusCode).entity(e).build()
    } catch (e: RuntimeException) {
        Response.status(Response.Status.BAD_REQUEST.statusCode).entity(e).build()
    }


    @DELETE
    @Path("{cidadeId}")
    @Transactional
    fun deletarCidade(
        @PathParam("estadoId") estadoId: Long,
        @PathParam("cidadeId") cidadeId: Long
    ) = try {
        service.deletarCidade(estadoId, cidadeId)
        Response.noContent().build()
    } catch (e: IllegalArgumentException) {
        Response.status(Response.Status.NOT_FOUND.statusCode).entity(e).build()
    }
}
