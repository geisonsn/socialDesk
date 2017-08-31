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

import org.jboss.resteasy.util.Base64;

import br.edu.ifam.socialdesk.business.FotoUsuarioBC;
import br.edu.ifam.socialdesk.business.UsuarioBC;
import br.edu.ifam.socialdesk.domain.FotoUsuario;
import br.edu.ifam.socialdesk.domain.Usuario;
import br.edu.ifam.socialdesk.domain.dto.UsuarioCadastroDTO;
import br.edu.ifam.socialdesk.domain.dto.UsuarioDTO;
import br.gov.frameworkdemoiselle.BadRequestException;
import br.gov.frameworkdemoiselle.NotFoundException;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.ValidatePayload;

@Path("/usuarios")
public class UsuarioREST {

	@Inject
	private UsuarioBC bc;

	@Inject
	private FotoUsuarioBC fotoUsuarioBC;

	// TODO Criar método getByNome que retorna um Usuario com foto

	@GET
	@Produces("application/json")
	public List<Usuario> find(@QueryParam("q") String query) throws Exception {
		List<Usuario> result;

		// if (Strings.isEmpty(query)) {
		// result = bc.findAll();
		// } else {
		result = bc.find(query);
		// }

		return result;
	}

	@POST
	@Produces("application/json")
	@Path("login")
	public Response login(UsuarioDTO usuarioRequest) throws Exception {
		Usuario usuario = bc.login(usuarioRequest.getEmail(), usuarioRequest.getSenha());
		if (usuario == null) {
			return Response.status(404).build();
		}
		return Response.status(200).entity(usuario).build();
	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public Usuario load(@PathParam("id") Long id) throws Exception {
		Usuario result = bc.load(id);

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
	public Response save(UsuarioCadastroDTO usuarioCadastroDTO, @Context UriInfo uriInfo) throws Exception {

		Usuario usuario = new Usuario(usuarioCadastroDTO.getNomeUsuario(), usuarioCadastroDTO.getSobrenome(),
				usuarioCadastroDTO.getEmail(), usuarioCadastroDTO.getSexo(), usuarioCadastroDTO.getSenha(),
				usuarioCadastroDTO.getDataNascimento());
		Long id = bc.save(usuario);
		URI location = uriInfo.getRequestUriBuilder().path(id.toString()).build();

		Usuario usuarioAtualizado = bc.load(id);

		if (usuarioCadastroDTO.getFoto() != null) {
			FotoUsuario foto = new FotoUsuario();
			foto.setUsuario(usuarioAtualizado);
			foto.setFoto(Base64.decode(usuarioCadastroDTO.getFoto()));
			fotoUsuarioBC.insert(foto);
		}

		return Response.created(location).entity(id).build();
	}

	private void checkId(Usuario body) throws BadRequestException {
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
	public void update(@PathParam("id") Long id, Usuario body) throws Exception {
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
