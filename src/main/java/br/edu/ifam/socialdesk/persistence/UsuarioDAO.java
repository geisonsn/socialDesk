package br.edu.ifam.socialdesk.persistence;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifam.socialdesk.domain.Usuario;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;

@PersistenceController
public class UsuarioDAO extends GenericDAO<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	public List<Usuario> find(String query) {

		final String hql = "select u from Usuario u order by u.nomeUsuario";

		TypedQuery<Usuario> createQuery = getEntityManager().createQuery(hql, Usuario.class);
		// createQuery.setParameter("nmUsuario", "%" + query + "%");

		return createQuery.getResultList();
	}

	/**
	 * Realiza a autenticação do usuário
	 * 
	 * @param email
	 * @param senha
	 * 
	 * @return usuário logado
	 */
	public Usuario login(String email, String senha) {
		final String hql = "select u from Usuario u where u.email = :email and u.senha = :senha ";
		try {
			Usuario usuario = getEntityManager().createQuery(hql, Usuario.class).setParameter("email", email)
					.setParameter("senha", senha).getSingleResult();
			return usuario;
		} catch (NoResultException e) {
			return null;
		}
	}

}
