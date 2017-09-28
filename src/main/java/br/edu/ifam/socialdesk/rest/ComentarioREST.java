package br.edu.ifam.socialdesk.rest;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.edu.ifam.socialdesk.business.ComentarioBC;
import br.edu.ifam.socialdesk.domain.Comentario;
import br.edu.ifam.socialdesk.domain.dto.ComentarioDTO;
import br.gov.frameworkdemoiselle.BadRequestException;
import br.gov.frameworkdemoiselle.NotFoundException;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Strings;
import br.gov.frameworkdemoiselle.util.ValidatePayload;

@Path("/comentario")
public class ComentarioREST {

	@Inject
	private ComentarioBC bc;

	@GET
	@Produces("application/json")
	public List<Comentario> find(@QueryParam("q") String query) throws Exception {
		List<Comentario> result;

		if (Strings.isEmpty(query)) {
			result = bc.findAll();
		} else {
			result = bc.find(query);
		}

		return result;
	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public Comentario load(@PathParam("id") Long id) throws Exception {
		Comentario result = bc.load(id);

		if (result == null) {
			throw new NotFoundException();
		}

		return result;
	}

	@POST
	@Transactional
	@ValidatePayload
	@Produces("application/json")
	@Consumes("application/json")
	public Response insert(Comentario body, @Context UriInfo uriInfo) throws Exception {
		checkId(body);

		Long id = bc.insert(body).getId();
		URI location = uriInfo.getRequestUriBuilder().path(id.toString()).build();

		return Response.created(location).entity(id).build();
	}

	private void checkId(Comentario body) throws BadRequestException {
		if (body.getId() != null) {
			throw new BadRequestException();
		}
	}

	@PUT
	// @LoggedIn
	@Path("{id}")
	@Transactional
	@ValidatePayload
	@Produces("application/json")
	@Consumes("application/json")
	public void update(@PathParam("id") Long id, Comentario body) throws Exception {
		checkId(body);
		load(id);

		body.setId(id);
		bc.update(body);
	}

	@DELETE
	// @LoggedIn
	@Path("{id}")
	@Transactional
	public void delete(@PathParam("id") Long id) throws Exception {
		load(id);
		bc.delete(id);
	}

	/*@GET
	@Path("idChamado/{idChamado}")
	@Produces("application/json")
	@Deprecated
	public List<Comentario> listarComentarios(@PathParam("idChamado") Long idChamado) throws Exception {
		List<Comentario> result = bc.listarComentariosUsuario(idChamado);

		if (result == null) {
			throw new NotFoundException();
		}

		return result;
	}*/
	
	@GET
	@Path("idChamado/{idChamado}")
	@Produces("application/json")
	public List<ComentarioDTO> listar(@PathParam("idChamado") Long idChamado) throws Exception {
		List<ComentarioDTO> result = bc.listar(idChamado);
		
		if (result == null) {
			throw new NotFoundException();
		}
		
		return result;
	}

}
