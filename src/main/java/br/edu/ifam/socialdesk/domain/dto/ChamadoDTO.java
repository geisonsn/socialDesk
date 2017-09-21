package br.edu.ifam.socialdesk.domain.dto;

import javax.validation.constraints.NotNull;

import br.edu.ifam.socialdesk.domain.Categoria;
import br.edu.ifam.socialdesk.domain.Chamado;
import br.edu.ifam.socialdesk.domain.Usuario;

public class ChamadoDTO {
	
	private Long idChamado;

	@NotNull
	private String descricao;

	@NotNull
	private Long idUsuario;
	@NotNull
	private Long idCategoria;

	private String foto;
	
	public Long getIdChamado() {
		return idChamado;
	}
	
	public void setIdChamado(Long idChamado) {
		this.idChamado = idChamado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Chamado getChamado() {
		Chamado chamado = new Chamado();
		chamado.setDescricao(getDescricao());

		if (this.idCategoria != null) {
			chamado.setCategoria(new Categoria());
			chamado.getCategoria().setId(idCategoria);
		}

		if (this.idUsuario != null) {
			chamado.setUsuario(new Usuario());
			chamado.getUsuario().setId(idUsuario);
		}
		/*
		 * ArquivoChamado arquivoChamado = new ArquivoChamado(); if (this.foto
		 * != null) { arquivoChamado.setFoto(getFoto());
		 * arquivoChamado.setChamado(chamado); }
		 */
		return chamado;
	}

}
