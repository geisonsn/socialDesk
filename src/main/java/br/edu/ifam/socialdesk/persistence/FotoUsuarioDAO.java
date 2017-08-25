package br.edu.ifam.socialdesk.persistence;

import java.util.List;

import javax.persistence.NoResultException;

import br.edu.ifam.socialdesk.domain.FotoUsuario;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;

@PersistenceController
public class FotoUsuarioDAO extends GenericDAO<FotoUsuario, Long> {

	private static final long serialVersionUID = 1L;

	public List<FotoUsuario> find(String query) {

		return null;
	}

	public FotoUsuario getByUsuario(Long idUsuario) {
		try {
			return (FotoUsuario) createQuery("select this from FotoUsuario this where this.usuario.id = :idUsuario")
					.setParameter("idUsuario", idUsuario)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}

}