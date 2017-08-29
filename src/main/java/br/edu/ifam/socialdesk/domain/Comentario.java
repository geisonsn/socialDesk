package br.edu.ifam.socialdesk.domain;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.edu.ifam.socialdesk.util.UtilDomain;

@Entity
@Table(name = "COMENTARIO")
public class Comentario implements Serializable {

	private static final long serialVersionUID = 1L;

	public Comentario() {

	}

	public Comentario(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Comentario(Long id, String descricao, Date dataComentario, String nomeUsuario, byte[] foto) {
		this.id = id;
		this.descricao = descricao;
		this.dataComentario = dataComentario;
		/*
		 * FotoUsuario fotoUsuario = new FotoUsuario();
		 * fotoUsuario.setId(idFotoUsuario);
		 */
		Usuario usuario = new Usuario();
		usuario.setNomeUsuario(nomeUsuario);
		this.usuario = usuario;
		this.foto = foto;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DATA_COMENTARIO")
	private Date dataComentario;

	@Column(name = "DESCRICAO")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "ID_CHAMADO")
	private Chamado chamado;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;

	@Transient
	private byte[] foto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataComentario() {
		return dataComentario;
	}

	public void setDataComentario(Date dataComentario) {
		this.dataComentario = dataComentario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getFotoBase64() throws IOException {
		if (foto != null) {
			String imagemReduzida = UtilDomain.redimensionaImagem(foto, 100, 100, "jpg");
			return imagemReduzida;
		}
		return null;
	}

}
