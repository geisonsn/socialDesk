package br.edu.ifam.socialdesk.persistence;

import java.util.List;

import javax.persistence.NoResultException;

import br.edu.ifam.socialdesk.domain.ArquivoChamado;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;

@PersistenceController
public class ArquivoChamadoDAO extends GenericDAO<ArquivoChamado, Long> {

	private static final long serialVersionUID = 1L;

	public List<ArquivoChamado> find(String query) {

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ArquivoChamado> findPorChamado(Long idChamado) {

		String hql = "select arq from ArquivoChamado arq where arq.chamado.id = :idChamado";

		return getEntityManager().createQuery(hql).setParameter("idChamado", idChamado).getResultList();

	}
	
	public ArquivoChamado getPorChamado(Long idChamado) {
		try {
			return getEntityManager().createQuery("select a from ArquivoChamado a where a.chamado.id = :idChamado", ArquivoChamado.class)
					.setParameter("idChamado", idChamado)
					.getSingleResult();
		} catch (NoResultException e) {}
		return null;
	}

}