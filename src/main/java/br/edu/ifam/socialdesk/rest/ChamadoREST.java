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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.util.Base64;

import br.edu.ifam.socialdesk.business.ArquivoChamadoBC;
import br.edu.ifam.socialdesk.business.ChamadoBC;
import br.edu.ifam.socialdesk.domain.ArquivoChamado;
import br.edu.ifam.socialdesk.domain.Chamado;
import br.edu.ifam.socialdesk.domain.Comentario;
import br.edu.ifam.socialdesk.domain.dto.ChamadoDTO;
import br.edu.ifam.socialdesk.domain.dto.ChamadoListaDTO;
import br.gov.frameworkdemoiselle.BadRequestException;
import br.gov.frameworkdemoiselle.NotFoundException;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.ValidatePayload;

@Path("/chamado")
public class ChamadoREST {

	@Inject
	private ChamadoBC bc;

	@Inject
	private ArquivoChamadoBC arquivoChamadoBC;

	@GET
	@Produces("application/json")
	public List<ChamadoListaDTO> find() throws Exception {
		return this.bc.find();
	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public Chamado load(@PathParam("id") Long id) throws Exception {
		Chamado result = bc.load(id);

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
	public Response save(ChamadoDTO form, @Context UriInfo uriInfo) throws Exception {
		Long id = bc.save(form.getChamado());

		Chamado chamado = bc.load(id);

		if (form.getFoto() != null) {
			ArquivoChamado arquivo = new ArquivoChamado();
			arquivo.setChamado(chamado);
			arquivo.setFoto(Base64.decode(form.getFoto()));
			arquivoChamadoBC.insert(arquivo);
		}

		URI location = uriInfo.getRequestUriBuilder().path(id.toString()).build();

		return Response.created(location).entity(id).build();
	}

	private void checkId(Chamado body) throws BadRequestException {
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
	public void update(@PathParam("id") Long id, Chamado body) throws Exception {
		checkId(body);
		load(id);

		body.setId(id);
		bc.update(body);
	}

	@PUT
	// @LoggedIn
	@Path("atualizaQtdeLike")
	@Transactional
	@ValidatePayload
	@Produces("application/json")
	@Consumes("application/json")
	public void updateQtdeLike(Chamado chamado) throws Exception {
		bc.updateQtdeLike(chamado);
	}

	@PUT
	// @LoggedIn
	@Path("fecharChamado/{idChamado}")
	@Transactional
	@Produces("application/json")
	public Response fecharChamado(@PathParam("idChamado") Long idChamado) throws Exception {
		try {
			bc.fecharChamado(idChamado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@DELETE
	// @LoggedIn
	@Path("{id}")
	@Produces("application/json")
	@Transactional
	public void delete(@PathParam("id") Long id) throws Exception {
		load(id);
		bc.excluirChamado(id);
	}

	@GET
	@Produces("application/json")
	@Path("listPorCategoria/{idCategoria}")
	public List<ChamadoListaDTO> listPorCategoria(@PathParam("idCategoria") Long idCategoria) throws Exception {
		List<ChamadoListaDTO> result;

		result = bc.listPorCategoria(idCategoria);

		return result;
	}

	@GET
	@Produces("application/json")
	@Path("listPorUsuario/{idUsuario}")
	public List<ChamadoListaDTO> listPorUsuario(@PathParam("idUsuario") Long idUsuario) throws Exception {
		List<ChamadoListaDTO> result;
		result = bc.listPorUsuario(idUsuario);
		return result;
	}

	@GET
	@Produces("application/json")
	@Path("listPorNomeUsuario/{nomeUsuario}")
	public List<Chamado> listPorNomeUsuario(@PathParam("nomeUsuario") String nomeUsuario) throws Exception {
		List<Chamado> result;
		result = bc.listPorNomeUsuario(nomeUsuario);
		return result;
	}

	@POST
	@Transactional
	@Path("comentario")
	@ValidatePayload
	@Produces("application/json")
	@Consumes("application/json")
	public void saveComentario(Comentario comentario) throws Exception {
		bc.saveComentario(comentario);
	}

}
