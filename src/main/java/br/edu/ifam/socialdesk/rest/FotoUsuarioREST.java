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

import br.edu.ifam.socialdesk.business.FotoUsuarioBC;
import br.edu.ifam.socialdesk.domain.FotoUsuario;
import br.edu.ifam.socialdesk.domain.dto.ChamadoListaDTO;
import br.gov.frameworkdemoiselle.BadRequestException;
import br.gov.frameworkdemoiselle.NotFoundException;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Strings;
import br.gov.frameworkdemoiselle.util.ValidatePayload;

@Path("/fotoUsuario")
public class FotoUsuarioREST {

	@Inject
	private FotoUsuarioBC bc;

	@GET
	@Produces("application/json")
	public List<FotoUsuario> find(@QueryParam("q") String query) throws Exception {
		List<FotoUsuario> result;

		if (Strings.isEmpty(query)) {
			result = bc.findAll();
		} else {
			result = bc.find(query);
		}

		return result;
	}

	@GET
	@Produces("application/json")
	@Path("getByUsuario/{idUsuario}")
	public FotoUsuario getByUsuario(@PathParam("idUsuario") Long idUsuario) throws Exception {
		FotoUsuario fotoUsuario;
		fotoUsuario = bc.getByUsuario(idUsuario);
		return fotoUsuario;
	}
	
	/*
	public FotoUsuario getByUsuario(Long idUsuario) {
		return getDelegate().getByUsuario(idUsuario);
	}
	
	*/
	@GET
	@Path("{id}")
	@Produces("application/json")
	public FotoUsuario load(@PathParam("id") Long id) throws Exception {
		FotoUsuario result = bc.load(id);

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
	public Response insert(FotoUsuario body, @Context UriInfo uriInfo) throws Exception {
		checkId(body);

		Long id = bc.insert(body).getId();
		URI location = uriInfo.getRequestUriBuilder().path(id.toString()).build();

		return Response.created(location).entity(id).build();
	}

	private void checkId(FotoUsuario body) throws BadRequestException {
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
	public void update(@PathParam("id") Long id, FotoUsuario body) throws Exception {
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

}
