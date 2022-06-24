package io.github.xahdy.apiCidades.resource

import io.github.xahdy.apiCidades.core.get
import io.github.xahdy.apiCidades.dto.CreateEstadoRequest
import io.github.xahdy.apiCidades.repository.EstadoRepository
import io.quarkus.panache.common.Page
import java.net.URI
import javax.persistence.PersistenceException
import javax.transaction.Transactional
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo


@Path("/estados/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class EstadoResource(
    private val repository: EstadoRepository,
    private val info: UriInfo
) {

    @GET
    @Path("{estadoId}")
    fun listarEstadoPorId(
        @PathParam("estadoId") estadoId: Long
    ) = Response.ok(repository[estadoId]).build()


    @GET
    fun listarTodos(
        @DefaultValue("0")
        @QueryParam("pagina") pagina: Int,
        @DefaultValue("99")
        @QueryParam("maxResults") maxResults: Int,
        @DefaultValue("nome")
        @QueryParam("campo") campo: String,
        @DefaultValue("")
        @QueryParam("busca") busca: String
    ): Response? {
        val page = Page.of(pagina, maxResults)
        try {
            return Response.ok(repository[page, campo, busca]).build()
        } catch (e: IllegalArgumentException) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build()
        } catch (e: PersistenceException) {
            return Response.status(Response.Status.NOT_FOUND).entity(e).build()
        }
    }

    @POST
    @Transactional
    fun cadastrarEstado(
        @Valid createEstadoRequest: CreateEstadoRequest
    ) =
        try {
            val estado = createEstadoRequest.create()
            repository.countDuplicados(estado.nome, estado.sigla)
            repository.persist(estado)
            val uri = URI.create(info.path.plus(estado.id))
            Response.created(uri).entity(estado).build()
        } catch (e: IllegalArgumentException) {
            Response.status(Response.Status.CONFLICT).entity(e.message).build()
        }


    @PUT
    @Path("{estadoId}")
    @Transactional
    fun atualizarEstadoPorId(
        @PathParam("estadoId") estadoId: Long,
        @Valid createEstadoRequest: CreateEstadoRequest
    ) = try {
        val estado = repository[estadoId]

        val nomeFormatado = createEstadoRequest.formatar(createEstadoRequest.nome)
        val siglaFormatado = createEstadoRequest.formatar(createEstadoRequest.sigla)

        repository.countDuplicados(nomeFormatado, siglaFormatado)
        estado.nome = nomeFormatado
        estado.sigla = siglaFormatado
        repository.persist(estado)
        Response.ok().build()
    } catch (e: IllegalArgumentException) {
        Response.status(Response.Status.CONFLICT).entity(e.message).build()
    }


    @DELETE
    @Path("{estadoId}")
    @Transactional
    fun deletarEstadoPorId(
        @PathParam("estadoId") estadoId: Long
    ) {
        repository.deleteById(estadoId)
        Response.noContent().build()
    }
}