package br.edu.ifam.socialdesk.business;

import java.util.List;

import br.edu.ifam.socialdesk.domain.Comentario;
import br.edu.ifam.socialdesk.persistence.ComentarioDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class ComentarioBC extends DelegateCrud<Comentario, Long, ComentarioDAO> {

	private static final long serialVersionUID = 1L;

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
	
	public List<Comentario> listarComentariosUsuario(Long idChamado) {
		return getDelegate().listarComentariosUsuario(idChamado);
	}
	
	
}
