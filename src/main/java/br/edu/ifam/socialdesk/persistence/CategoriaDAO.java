package br.edu.ifam.socialdesk.persistence;

import java.util.List;

import br.edu.ifam.socialdesk.domain.Categoria;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;

@PersistenceController
public class CategoriaDAO extends GenericDAO<Categoria, Long> {

	private static final long serialVersionUID = 1L;

	public List<Categoria> find(String query) {

		return null;
	}

}