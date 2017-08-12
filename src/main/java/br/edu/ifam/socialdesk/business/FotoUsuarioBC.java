package br.edu.ifam.socialdesk.business;

import java.util.List;

import br.edu.ifam.socialdesk.domain.FotoUsuario;
import br.edu.ifam.socialdesk.persistence.FotoUsuarioDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class FotoUsuarioBC extends DelegateCrud<FotoUsuario, Long, FotoUsuarioDAO> {

	private static final long serialVersionUID = 1L;

	public List<FotoUsuario> find(String query) {
		return getDelegate().find(query);
	}
}
