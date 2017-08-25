package br.edu.ifam.socialdesk.domain.dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.socialdesk.domain.ArquivoChamado;
import br.edu.ifam.socialdesk.domain.Chamado;
import br.edu.ifam.socialdesk.domain.Comentario;
import br.edu.ifam.socialdesk.domain.FotoUsuario;

public class ChamadoListaDTO {

	private Chamado chamado;
	private Long qtdComentarios;
	private List<ArquivoChamado> arquivos;
	private String urlArquivo;
	private String urlFoto;
	private List<Comentario> listComentario = new ArrayList<>();

	public ChamadoListaDTO() {
	}

	public ChamadoListaDTO(Chamado chamado, Long qtdComentarios, List<ArquivoChamado> arquivos,
			List<Comentario> listComentario, FotoUsuario fotoUsuario) throws IOException {
		super();
		this.chamado = chamado;
		this.qtdComentarios = qtdComentarios;
		this.arquivos = arquivos;
		this.listComentario = listComentario;
		for (ArquivoChamado arquivoChamado : arquivos) {
			this.setUrlArquivo(arquivoChamado.getFotoBase64());
		}
		
		if (fotoUsuario != null) {
			this.urlFoto = fotoUsuario.getFotoBase64();
		}
		
		
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

	public List<ArquivoChamado> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<ArquivoChamado> arquivos) {
		this.arquivos = arquivos;
	}

	public List<Comentario> getListComentario() {
		return listComentario;
	}

	public void setListComentario(List<Comentario> listComentario) {
		this.listComentario = listComentario;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	
}
