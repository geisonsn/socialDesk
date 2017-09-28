package br.edu.ifam.socialdesk.domain.dto;

import java.io.Serializable;

public class ChamadoListaDTO2 implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long idChamado;
	private String descricao;
	private String imagem;
	private String data;
	private String status;
	private CategoriaDTO categoria;
	private UsuarioDTO usuario;
	private Integer curtidas;
	private Integer comentarios;

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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public Integer getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(Integer curtidas) {
		this.curtidas = curtidas;
	}

	public Integer getComentarios() {
		return comentarios;
	}
	
	public void setComentarios(Integer comentarios) {
		this.comentarios = comentarios;
	}

}
