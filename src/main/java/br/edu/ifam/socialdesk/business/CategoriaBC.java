package br.edu.ifam.socialdesk.business;

import java.util.List;

import br.edu.ifam.socialdesk.domain.Categoria;
import br.edu.ifam.socialdesk.persistence.CategoriaDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class CategoriaBC extends DelegateCrud<Categoria, Long, CategoriaDAO> {

	private static final long serialVersionUID = 1L;

	public List<Categoria> find(String query) {
		return getDelegate().find(query);
	}

}
