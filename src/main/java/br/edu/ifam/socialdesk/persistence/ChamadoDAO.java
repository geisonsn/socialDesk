package br.edu.ifam.socialdesk.persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import br.edu.ifam.socialdesk.constant.Constants;
import br.edu.ifam.socialdesk.domain.Chamado;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;

@PersistenceController
public class ChamadoDAO extends GenericDAO<Chamado, Long> {

	private static final long serialVersionUID = 1L;

	public List<Chamado> find(String query) {

		final String hql = "select u from Chamado u where u.descricao like :descricao order by u.dataCriacao desc";

		TypedQuery<Chamado> createQuery = getEntityManager().createQuery(hql, Chamado.class);
		createQuery.setParameter("descricao", "%" + query + "%");

		return createQuery.getResultList();
	}

	public List<Chamado> listPorCategoria(Long idCategoria) {

		final String hql = "SELECT chamado FROM Chamado chamado WHERE chamado.status.siglaStatus = :statusFechado "
				+ " AND chamado.categoria.id = :idCategoria ORDER BY chamado.dataCriacao ";

		TypedQuery<Chamado> createQuery = getEntityManager().createQuery(hql, Chamado.class);
		createQuery.setParameter("statusFechado", Constants.STATUS_FECHADO);
		createQuery.setParameter("idCategoria", idCategoria);

		return createQuery.getResultList();
	}

	public List<Chamado> listPorUsuario(Long idUsuario) {

		final String hql = "SELECT chamado FROM Chamado chamado WHERE chamado.status.siglaStatus = :statusAberto "
				+ " AND chamado.usuario.id = :idUsuario ORDER BY chamado.dataCriacao DESC";

		TypedQuery<Chamado> createQuery = getEntityManager().createQuery(hql, Chamado.class);
		createQuery.setParameter("statusAberto", Constants.STATUS_ABERTO);
		createQuery.setParameter("idUsuario", idUsuario);

		return createQuery.getResultList();
	}

	public List<Chamado> listPorNomeUsuario(String nomeUsuario) {

		final String hql = "SELECT chamado FROM Chamado chamado WHERE "
				+ " chamado.usuario.nomeUsuario like :nomeUsuario ORDER BY chamado.dataCriacao DESC";

		TypedQuery<Chamado> createQuery = getEntityManager().createQuery(hql, Chamado.class);
		createQuery.setParameter("nomeUsuario", "%" + nomeUsuario + "%");

		return createQuery.getResultList();
	}
	
	public List<Chamado> list() {
		String sql = new StringBuilder()
			.append(" SELECT c FROM Chamado c ORDER BY c.dataCriacao DESC")
			.toString();
		return getEntityManager().createQuery(sql, Chamado.class)
				.getResultList();
	}

}