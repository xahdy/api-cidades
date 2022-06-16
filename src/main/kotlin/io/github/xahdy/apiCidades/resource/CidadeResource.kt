package io.github.xahdy.apiCidades.resource

import io.github.xahdy.apiCidades.dto.CreateCidadeRequest
import io.github.xahdy.apiCidades.service.CidadeService
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/estados/{estadoId}/cidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CidadeResource(private val service: CidadeService) {

    @GET
    fun listarTodasCidadesPorEstado(@PathParam("estadoId") estadoId: Long) =
        Response.ok(service.listarTodasCidadesEstado(estadoId)).build()

    @GET
    @Path("{cidadeId}")
    fun listarCidadePorId(
        @PathParam("estadoId") estadoId: Long,
        @PathParam("cidadeId") cidadeId: Long
    ): Response {
        try {
            val cidade = service.listarCidadeId(estadoId, cidadeId)
            return Response.ok(cidade).build()
        } catch (e: IllegalArgumentException) {
            return Response.status(Response.Status.NOT_FOUND.statusCode).build()
        }
    }

    @POST
    @Transactional
    fun cadastrarCidade(
        @PathParam("estadoId") estadoId: Long,
        createCidadeRequest: CreateCidadeRequest
    ) = Response.status(Response.Status.CREATED.statusCode)
        .entity(service.cadastrarCidade(estadoId, createCidadeRequest)).build();

    @PUT
    @Path("{cidadeId}")
    @Transactional
    fun atualizarEstadoPorId(
        @PathParam("estadoId") estadoId: Long,
        @PathParam("cidadeId") cidadeId: Long,
        createCidadeRequest: CreateCidadeRequest
    ) = Response.ok(service.atualizarCidade(estadoId, cidadeId, createCidadeRequest)).build()


    @DELETE
    @Path("{cidadeId}")
    @Transactional
    fun deletarCidade(
        @PathParam("estadoId") estadoId: Long,
        @PathParam("cidadeId") cidadeId: Long
    ): Response {
        try {
            val cidade = service.deletarCidade(estadoId, cidadeId)
            return Response.noContent().build()
        } catch (e: IllegalArgumentException) {
            return Response.status(Response.Status.NOT_FOUND.statusCode).build()
        }
    }
}