package br.edu.ifam.socialdesk.rest;

import java.util.Date;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.edu.ifam.socialdesk.business.ChamadoBC;
import br.edu.ifam.socialdesk.domain.Chamado;
import br.edu.ifam.socialdesk.domain.Comentario;
import br.edu.ifam.socialdesk.domain.dto.ChamadoAuxDTO;
import br.edu.ifam.socialdesk.domain.dto.ChamadoDTO;
import br.edu.ifam.socialdesk.domain.dto.ChamadoDTO2;
import br.edu.ifam.socialdesk.domain.dto.ChamadoListaDTO;
import br.edu.ifam.socialdesk.domain.dto.ChamadoListaDTO2;
import br.gov.frameworkdemoiselle.NotFoundException;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.ValidatePayload;

@Path("/chamado")
public class ChamadoREST {

	@Inject
	private ChamadoBC bc;

	/*@GET
	@Produces("application/json")
	public List<ChamadoListaDTO> find() throws Exception {
		return this.bc.find();
	}*/
	
	@GET
	@Produces("application/json")
	public List<ChamadoListaDTO2> list() throws Exception {
		return bc.list();
	}

	/*@GET
	@Path("{id}")
	@Produces("application/json")
	public Chamado load(@PathParam("id") Long id) throws Exception {
		Chamado result = bc.load(id);

		if (result == null) {
			throw new NotFoundException();
		}

		return result;
	}*/
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	public ChamadoDTO2 load(@PathParam("id") Long id) throws Exception {
		ChamadoDTO2 chamado = bc.get(id);
		
		if (chamado == null) {
			throw new NotFoundException();
		}
		
		return chamado;
	}

	@GET
	@Path("comComentarios/{id}")
	@Produces("application/json")
	public ChamadoAuxDTO loadComComentarios(@PathParam("id") Long id) throws Exception {

		ChamadoAuxDTO result = bc.loadComComentario(id);

		if (result == null) {
			throw new NotFoundException();
		}

		return result;
	}

	@POST
	@ValidatePayload
	@Produces("application/json")
	@Consumes("application/json")
	public Response save(ChamadoDTO form) throws Exception {
		
		Chamado chamado = bc.salvarChamado(form);
		
		if (form.getIdChamado() == null) {
			return Response.status(Status.CREATED).entity(chamado.getId()).build();
		}
		
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@PUT
	// @LoggedIn
	@Path("curtir/{id}")
	@Transactional
	@ValidatePayload
	@Produces("application/json")
	public void updateQtdeLike(@PathParam("id") Long id) throws Exception {
		bc.curtir(id);
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
	@Deprecated
	public List<ChamadoListaDTO> listPorCategoria(@PathParam("idCategoria") Long idCategoria) throws Exception {
		List<ChamadoListaDTO> result;

		result = bc.listPorCategoria(idCategoria);

		return result;
	}

	@GET
	@Produces("application/json")
	@Path("listPorUsuario/{idUsuario}")
	public List<ChamadoListaDTO2> listPorUsuario(@PathParam("idUsuario") Long idUsuario) throws Exception {
		List<ChamadoListaDTO2> result;
		result = bc.listPorUsuario(idUsuario);
		return result;
	}
	
	// by Rosangela
	@GET
	@Produces("application/json")
	@Path("listPorNomeUsuario/{nomeUsuario}")
	public List<ChamadoListaDTO2> listPorNomeUsuario(@PathParam("nomeUsuario") String nomeUsuario) throws Exception {
		List<ChamadoListaDTO2> result;
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
		comentario.setDataComentario(new Date());
		bc.saveComentario(comentario);
	}

}
