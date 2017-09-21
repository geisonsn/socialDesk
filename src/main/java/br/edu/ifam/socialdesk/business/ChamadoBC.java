package br.edu.ifam.socialdesk.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.socialdesk.constant.Constants;
import br.edu.ifam.socialdesk.domain.ArquivoChamado;
import br.edu.ifam.socialdesk.domain.Chamado;
import br.edu.ifam.socialdesk.domain.Comentario;
import br.edu.ifam.socialdesk.domain.FotoUsuario;
import br.edu.ifam.socialdesk.domain.Status;
import br.edu.ifam.socialdesk.domain.dto.ChamadoAuxDTO;
import br.edu.ifam.socialdesk.domain.dto.ChamadoDTO;
import br.edu.ifam.socialdesk.domain.dto.ChamadoListaDTO;
import br.edu.ifam.socialdesk.exception.BusinessException;
import br.edu.ifam.socialdesk.persistence.ChamadoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@BusinessController
public class ChamadoBC extends DelegateCrud<Chamado, Long, ChamadoDAO> {

	@Inject
	private ComentarioBC comentarioBC;

	@Inject
	private ArquivoChamadoBC arquivoChamadoBC;

	@Inject
	private FotoUsuarioBC fotoUsuarioBC;

	@Inject
	private StatusBC statusBC;

	private static final long serialVersionUID = 1L;

	public List<Chamado> find(String query) {
		return getDelegate().find(query);
	}

	public List<ChamadoListaDTO> find() throws IOException {
		List<Chamado> listChamado = this.getDelegate().find("");

		return buildListaChamado(listChamado);
	}

	private List<ChamadoListaDTO> buildListaChamado(List<Chamado> listChamado) throws IOException {
		List<ChamadoListaDTO> result = new ArrayList<>();

		for (Chamado chamado : listChamado) {
			Long nrComentarios = this.comentarioBC.contarComentarios(chamado.getId());

			List<ArquivoChamado> arquivos = this.arquivoChamadoBC.findPorChamado(chamado.getId());
			List<Comentario> comentarios = this.comentarioBC.listarComentariosUsuario(chamado.getId());
			FotoUsuario foto = this.fotoUsuarioBC.getByUsuario(chamado.getUsuario().getId());

			result.add(new ChamadoListaDTO(chamado, nrComentarios, arquivos, comentarios, foto));

		}

		return result;
	}

	public ChamadoAuxDTO loadComComentario(Long idChamado) throws IOException {
		Chamado chamado = this.getDelegate().load(idChamado);
		List<Comentario> listComentario = this.comentarioBC.listarComentariosUsuario(chamado.getId());
		ChamadoAuxDTO chamadoAuxDTO = new ChamadoAuxDTO(chamado, listComentario);
		return chamadoAuxDTO;
	}

	/**
	 * Listar chamados por categoria
	 * 
	 * @param idCategoria
	 */
	public List<ChamadoListaDTO> listPorCategoria(Long idCategoria) {
		List<ChamadoListaDTO> result = new ArrayList<>();
		List<Chamado> listPorCategoria = getDelegate().listPorCategoria(idCategoria);
		for (Chamado chamado : listPorCategoria) {
			result.add(new ChamadoListaDTO(chamado, this.comentarioBC.contarComentarios(chamado.getId()),
					"img/apple.jpg"));
		}

		return result;
	}

	/**
	 * Listar chamados por idUsuário
	 * 
	 * @param idUsuario
	 */
	public List<ChamadoListaDTO> listPorUsuario(Long idUsuario) {
		List<ChamadoListaDTO> result = new ArrayList<>();
		List<Chamado> listPorCategoria = getDelegate().listPorUsuario(idUsuario);
		for (Chamado chamado : listPorCategoria) {
			result.add(new ChamadoListaDTO(chamado, this.comentarioBC.contarComentarios(chamado.getId()),
					"img/apple.jpg"));
		}

		return result;

	}

	/**
	 * Listar chamados por nome de usuário
	 * 
	 * @param nomeUsuario
	 * @throws IOException
	 */
	public List<ChamadoListaDTO> listPorNomeUsuario(String nomeUsuario) throws IOException {
		List<Chamado> result = getDelegate().listPorNomeUsuario(nomeUsuario);

		return buildListaChamado(result);
	}

	/**
	 * Exclui o chamado
	 * 
	 * @param idChamado
	 * @throws BusinessException
	 */
	public void excluirChamado(Long idChamado) throws BusinessException {
		Long qtdeComentarios = comentarioBC.contarComentarios(idChamado);
		if (qtdeComentarios > 1) {
			throw new BusinessException("Não é possível excluir o chamado por possuir comentários.");
		}

		// arquivoChamadoBC.deletePorChamado(idChamado);

		this.delete(idChamado);
	}

	/**
	 * Fechar o chamado
	 * 
	 * @param idChamado
	 * @throws BusinessException
	 */
	@Transactional
	public void fecharChamado(Long idChamado) throws BusinessException {
		Long qtdeComentarios = comentarioBC.contarComentarios(idChamado);
		if (qtdeComentarios < 1) {
			throw new BusinessException("Não é possível fechar o chamado,pois ainda não está em atendimento");
		}

		Chamado chamado = load(idChamado);

		Status status = statusBC.getPorSigla(Constants.STATUS_FECHADO);
		chamado.setStatus(status);
		getDelegate().update(chamado);
	}

	/**
	 * Salva as informações do chamado
	 * @param form
	 * @return
	 */
	@Transactional
	public Chamado salvarChamado(ChamadoDTO form) {
		
		Chamado payload = form.getChamado();
		
		if (form.getIdChamado() == null) {
			Status status = statusBC.getPorSigla(Constants.STATUS_ABERTO);
			payload.setStatus(status);
			payload.setDataCriacao(new Date());
			getDelegate().insert(payload);
		} else {
			Chamado chamado = this.load(form.getIdChamado());
			chamado.setCategoria(payload.getCategoria());
			chamado.setDescricao(form.getDescricao());
			getDelegate().update(chamado);
			payload = chamado;
		}
		
		
		arquivoChamadoBC.salvarArquivo(form.getFoto(), payload);
		
		return payload;
	}

	/**
	 * Metodo para inserir comentarios no chamado Adiciona os comentarios em um
	 * arrayList e salva o objeto por cascade.
	 * 
	 * @param comentario
	 */
	public void saveComentario(Comentario comentario) {

		comentarioBC.insert(comentario);

	}

	/**
	 * Atualiza a quantidade de curtidas de um Chamado
	 * 
	 * @param chamado
	 */
	public void updateQtdeLike(Chamado chamado) {
		Chamado chamadoBanco = this.load(chamado.getId());
		Long qtdeLikeAtualizada = (long) 0;
		if (chamadoBanco.getQuantidadeCurtida() == null) {
			qtdeLikeAtualizada = 1L;
		} else {
			qtdeLikeAtualizada = chamadoBanco.getQuantidadeCurtida() + 1;
		}

		chamadoBanco.setQuantidadeCurtida(qtdeLikeAtualizada);
		getDelegate().update(chamadoBanco);

	}
}
