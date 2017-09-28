package br.edu.ifam.socialdesk.domain.dto;

import java.io.Serializable;

public class ComentarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String comentario;
	private String data;
	private UsuarioDTO usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

}
