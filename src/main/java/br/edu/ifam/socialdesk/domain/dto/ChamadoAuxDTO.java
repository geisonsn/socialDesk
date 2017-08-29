package br.edu.ifam.socialdesk.domain.dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.socialdesk.domain.Chamado;
import br.edu.ifam.socialdesk.domain.Comentario;

public class ChamadoAuxDTO {

	private Chamado chamado;
	private List<Comentario> listComentario = new ArrayList<>();

	public ChamadoAuxDTO() {
	}

	public ChamadoAuxDTO(Chamado chamado, List<Comentario> listComentario) throws IOException {
		super();
		this.chamado = chamado;
		this.listComentario = listComentario;

	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public List<Comentario> getListComentario() {
		return listComentario;
	}

	public void setListComentario(List<Comentario> listComentario) {
		this.listComentario = listComentario;
	}

}
