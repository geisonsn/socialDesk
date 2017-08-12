package br.edu.ifam.socialdesk.domain.dto;

import java.io.Serializable;

public class SexoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String descricao;

	public SexoDTO() {
	}

	public SexoDTO(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
