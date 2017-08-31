package br.edu.ifam.socialdesk.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.edu.ifam.socialdesk.domain.enums.Sexo;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	public Usuario() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NOME", nullable = false, length = 255)
	private String nomeUsuario;

	@Column(name = "SOBRENOME", length = 255)
	private String sobrenome;

	@Column(name = "EMAIL", length = 255)
	private String email;

	@Column(name = "SEXO")
	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	@Column(name = "SENHA", length = 10)
	private String senha;

	@Column(name = "DATANASCIMENTO")
	private Date dataNascimento;

	@Transient
	private String fotoUsuarioBase64;

	/*
	 * @Transient private byte[] foto;
	 */

	public String getFotoUsuarioBase64() {
		return fotoUsuarioBase64;
	}

	public void setFotoUsuarioBase64(String fotoUsuarioBase64) {
		this.fotoUsuarioBase64 = fotoUsuarioBase64;
	}

	public Usuario(String nomeUsuario, String sobrenome, String email, Sexo sexo, String senha, Date dataNascimento) {
		super();
		this.nomeUsuario = nomeUsuario;
		this.sobrenome = sobrenome;
		this.email = email;
		this.sexo = sexo;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/*
	 * public byte[] getFoto() { return foto; }
	 * 
	 * public void setFoto(byte[] foto) { this.foto = foto; }
	 * 
	 * public String getFotoBase64() throws IOException { if (foto != null) {
	 * String imagemReduzida = UtilDomain.redimensionaImagem(foto, 100, 100,
	 * "jpg"); return imagemReduzida; } return null; }
	 */

}
