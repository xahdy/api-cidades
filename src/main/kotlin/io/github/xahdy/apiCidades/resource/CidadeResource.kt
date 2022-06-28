package io.github.xahdy.apiCidades.resource

import io.github.xahdy.apiCidades.core.formatar
import io.github.xahdy.apiCidades.core.get
import io.github.xahdy.apiCidades.dto.CidadeResponse
import io.github.xahdy.apiCidades.dto.CreateCidadeRequest
import io.github.xahdy.apiCidades.repository.CidadeRepository
import io.github.xahdy.apiCidades.repository.EstadoRepository
import java.net.URI
import javax.transaction.Transactional
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo

@Path("/cidades/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class CidadeResource(
    private val repository: CidadeRepository,
    private val estadoRepository: EstadoRepository,
    private val info: UriInfo
) {
    @GET
    fun listarTodasCidades() = Response.ok(repository.findAll().list().map { CidadeResponse(it) }).build()

    @GET
    @Path("{cidadeId}")
    fun listarCidadePorId(
        @PathParam("cidadeId") cidadeId: Long
    ) =  Response.ok(repository[cidadeId]).build()
//        Response.ok(CidadeResponse().todasCidades(repository[cidadeId])).build()




    @POST
    @Transactional
    fun cadastrarCidade(
        @Valid createCidadeRequest: CreateCidadeRequest
    ): Response? {
        try {
            val estado = estadoRepository.cidadeEncontrarEstado(createCidadeRequest.estado)

            val cidade =
                createCidadeRequest.create(
                    estado = estado
                )
            repository.countDuplicados(formatar(createCidadeRequest.nome), estado)
            repository.persist(cidade)

            val uri = URI.create(info.path.plus(cidade.id))
            return Response.created(uri).entity(cidade).build()
        } catch (e: IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.message).build()
        }
    }


    @PUT
    @Path("{cidadeId}")
    @Transactional
    fun atualizarEstadoPorId(
        @PathParam("cidadeId") cidadeId: Long,
        @Valid createCidadeRequest: CreateCidadeRequest
    ): Response {
        val cidade = repository[cidadeId]
        val estado = estadoRepository.cidadeEncontrarEstado(createCidadeRequest.estado)
        val nomeFormatado = formatar(createCidadeRequest.nome)


        cidade.nome = nomeFormatado
        cidade.estado = estado

        return Response.ok(cidade).build()
    }



    @DELETE
    @Path("{cidadeId}")
    @Transactional
    fun deletarCidade(
        @PathParam("cidadeId") cidadeId: Long
    ): Response {
        repository.deleteById(cidadeId)
        return Response.noContent().build()
    }
}

