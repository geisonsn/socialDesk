package br.edu.ifam.socialdesk.business;

import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.socialdesk.domain.Chamado;
import br.edu.ifam.socialdesk.domain.FotoUsuario;
import br.edu.ifam.socialdesk.domain.Usuario;
import br.edu.ifam.socialdesk.domain.dto.UsuarioDTO;
import br.edu.ifam.socialdesk.persistence.UsuarioDAO;
import br.edu.ifam.socialdesk.util.ModelMapperUtil;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> {

	private static final long serialVersionUID = 1L;

	@Inject 
	private FotoUsuarioBC fotoUsuarioBC;

	/**
	 * Lista usuários
	 * 
	 * @param query
	 * @return
	 */
	public List<Usuario> find(String query) {
		return getDelegate().find(query);
	}

	/**
	 * Realiza a autenticação do usuário
	 * 
	 * @param email
	 * @param senha
	 * 
	 * @return Usuário logado
	 */
	public Usuario login(String email, String senha) {
		return getDelegate().login(email, senha);
	}

	/**
	 * Inclusão de Usuário
	 * 
	 * @param usuario
	 * @return
	 */
	public Long save(Usuario usuario) {
		Long id = null;

		if (usuario.getId() == null) {
			id = getDelegate().insert(usuario).getId();
		} else {
			id = getDelegate().update(usuario).getId();
		}
		System.out.println("idUsuario" + usuario.getId());

		return id;
	}
	
	public UsuarioDTO toUsuarioDTO(Usuario source) {
		UsuarioDTO usuario = ModelMapperUtil.map(source, UsuarioDTO.class, null);
		FotoUsuario fotoUsuario = fotoUsuarioBC.getByUsuario(usuario.getId());
		if (fotoUsuario != null) {
			usuario.setFoto(fotoUsuario.getFotoBase64());
		}
		return usuario;
	}

}
