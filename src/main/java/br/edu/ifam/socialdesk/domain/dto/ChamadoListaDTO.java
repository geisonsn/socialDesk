package br.edu.ifam.socialdesk.domain.dto;

import br.edu.ifam.socialdesk.domain.Chamado;

public class ChamadoListaDTO {

	private Chamado chamado;
	private Long qtdComentarios;
	private String urlArquivo;

	public ChamadoListaDTO() {
	}

	public ChamadoListaDTO(Chamado chamado, Long qtdComentarios, String urlArquivo) {
		super();
		this.chamado = chamado;
		this.qtdComentarios = qtdComentarios;
		this.urlArquivo = urlArquivo;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public Long getQtdComentarios() {
		return qtdComentarios;
	}

	public void setQtdComentarios(Long qtdComentarios) {
		this.qtdComentarios = qtdComentarios;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}
}