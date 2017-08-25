package br.edu.ifam.socialdesk.domain;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.edu.ifam.socialdesk.util.UtilDomain;

@Entity
@Table(name = "FOTO_USUARIO")
public class FotoUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	public FotoUsuario() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;

	@Lob
	@Column(name = "FOTO")
	private byte[] foto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFotoBase64() throws IOException {
		if (foto != null) {
			String imagemReduzida = UtilDomain.redimensionaImagem(foto, 100, 100, "jpg");
			return imagemReduzida;
		}
		return null;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

}