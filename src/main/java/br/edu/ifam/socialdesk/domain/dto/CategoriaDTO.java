package br.edu.ifam.socialdesk.domain.dto;

import java.io.Serializable;

public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
