package br.edu.ifam.socialdesk.persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import br.edu.ifam.socialdesk.domain.Comentario;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;

@PersistenceController
public class ComentarioDAO extends GenericDAO<Comentario, Long> {

	private static final long serialVersionUID = 1L;

	public List<Comentario> find(String query) {

		return null;
	}

	/**
	 * Conta a quantidade de comentarios de um chamado
	 * 
	 * @param idChamado
	 * @return
	 */
	public Long contarComentarios(Long idChamado) {
		String hql = "select count(c) from Comentario c where c.chamado.id = :idChamado";
		Long quantidadeComentarios = getEntityManager().createQuery(hql, Long.class)
				.setParameter("idChamado", idChamado).getSingleResult();
		return quantidadeComentarios;
	}

	/**
	 * Listar comentarios por idChamado.
	 * 
	 * @param idChamado
	 * @return
	 */
	public List<Comentario> listarComentarios(Long idChamado) {

		final String hql = "SELECT c FROM Comentario c WHERE c.chamado.id = :idChamado order by data_comentario desc";

		TypedQuery<Comentario> createQuery = getEntityManager().createQuery(hql, Comentario.class);
		createQuery.setParameter("idChamado", idChamado);

		return createQuery.getResultList();
	}

	public List<Comentario> listarComentariosUsuario(Long idChamado) {

		final String hql = "select new Comentario(comentario.id, comentario.descricao, comentario.dataComentario, comentario.usuario.nomeUsuario, fotoUsuario.foto) "
				+ " from Comentario comentario, FotoUsuario fotoUsuario where comentario.chamado.id = :idChamado and (fotoUsuario.usuario.id = comentario.usuario.id or fotoUsuario.usuario is null ) ORDER BY comentario.id DESC";

		TypedQuery<Comentario> createQuery = getEntityManager().createQuery(hql, Comentario.class);
		createQuery.setParameter("idChamado", idChamado);

		return createQuery.getResultList();
	}

}