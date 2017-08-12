package br.edu.ifam.socialdesk.business;

import java.util.List;

import br.edu.ifam.socialdesk.domain.Status;
import br.edu.ifam.socialdesk.persistence.StatusDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class StatusBC extends DelegateCrud<Status, Long, StatusDAO> {

	private static final long serialVersionUID = 1L;

	public List<Status> find(String query) {
		return getDelegate().find(query);
	}

	public Status getPorSigla(String siglaStatus) {
		return getDelegate().getPorSigla(siglaStatus);
	}
}
