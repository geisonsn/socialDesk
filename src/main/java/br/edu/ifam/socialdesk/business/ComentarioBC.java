package br.edu.ifam.socialdesk.business;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.socialdesk.domain.Comentario;
import br.edu.ifam.socialdesk.domain.dto.ComentarioDTO;
import br.edu.ifam.socialdesk.persistence.ComentarioDAO;
import br.edu.ifam.socialdesk.util.Data;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class ComentarioBC extends DelegateCrud<Comentario, Long, ComentarioDAO> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioBC usuarioBC;

	public List<Comentario> find(String query) {
		return getDelegate().find(query);
	}

	/**
	 * Conta a quantidade de comentarios de um chamado
	 * 
	 * @param idChamado
	 * @return
	 */
	public Long contarComentarios(Long idChamado) {
		return getDelegate().contarComentarios(idChamado);
	}

	/**
	 * Inclui/edita um comentário
	 * 
	 * @param comentario
	 * @return
	 */
	public Long save(Comentario comentario) {
		Long id = null;

		if (comentario.getId() == null) {
			id = getDelegate().insert(comentario).getId();
		} else {
			id = getDelegate().update(comentario).getId();
		}

		return id;
	}

	/**
	 * Método resposavel por realizar consultar de comentarios por id chamado
	 * 
	 * @author Waldecleber
	 * @param idChamado
	 * @return
	 */
	public List<Comentario> listarComentarios(Long idChamado) {
		return getDelegate().listarComentarios(idChamado);
	}
	
	public List<ComentarioDTO> listar(Long idChamado) {
		List<Comentario> comentarios = getDelegate().listarComentarios(idChamado);
		List<ComentarioDTO> list = new ArrayList<>();
		for (Comentario c : comentarios) {
			ComentarioDTO comentario = new ComentarioDTO();
			comentario.setId(c.getId());
			comentario.setComentario(c.getDescricao());
			comentario.setUsuario(usuarioBC.toUsuarioDTO(c.getUsuario()));
			comentario.setData(Data.format(c.getDataComentario(), Data.DatePattern.DD_MM_YYYY_HH_mm_ss_FORMATTED1));
			list.add(comentario);
		}
		return list;
	}
	
	public List<Comentario> listarComentariosUsuario(Long idChamado) {
		return getDelegate().listarComentariosUsuario(idChamado);
	}
	
	
}
