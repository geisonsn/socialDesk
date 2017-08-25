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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.codec.binary.Base64;

import br.edu.ifam.socialdesk.util.ImagemUtil;
import br.edu.ifam.socialdesk.util.UtilDomain;

@Entity
@Table(name = "ARQUIVO_CHAMADO")
public class ArquivoChamado implements Serializable {

	private static final long serialVersionUID = 1L;

	public ArquivoChamado() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ID_CHAMADO")
	private Chamado chamado;

	@Lob
	@Column(name = "FOTO")
	private byte[] foto;

	@Lob
	@Column(name = "VIDEO")
	private String video;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

//	public String getFotoBase64() {
//		if (foto != null) {
//			byte[] encodeBase64 = Base64.encodeBase64(foto);
//			return new String(encodeBase64);
//		}
//		return null;
//	}
	
	public String getFotoBase64() throws IOException{
		if (foto != null) {
			String imagemReduzida = UtilDomain.redimensionaImagem(foto, 100, 100, "jpg");
			return imagemReduzida;
		}
		return null;
	}
	

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

}