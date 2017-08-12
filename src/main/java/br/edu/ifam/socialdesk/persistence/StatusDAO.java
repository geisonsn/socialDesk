package br.edu.ifam.socialdesk.persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import br.edu.ifam.socialdesk.domain.Status;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;

@PersistenceController
public class StatusDAO extends GenericDAO<Status, Long> {

	private static final long serialVersionUID = 1L;

	public List<Status> find(String query) {

		return null;
	}

	public Status getPorSigla(String siglaStatus) {

		final String hql = "SELECT status FROM Status status WHERE status.siglaStatus = :siglaStatus";

		TypedQuery<Status> createQuery = getEntityManager().createQuery(hql, Status.class);
		createQuery.setParameter("siglaStatus", siglaStatus);

		return createQuery.getSingleResult();
	}

}