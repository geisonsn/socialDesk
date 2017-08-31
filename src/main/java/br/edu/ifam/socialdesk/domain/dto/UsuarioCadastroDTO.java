package br.edu.ifam.socialdesk.domain.dto;

import java.io.Serializable;
import java.util.Date;

import br.edu.ifam.socialdesk.domain.enums.Sexo;

public class UsuarioCadastroDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nomeUsuario;
	private String sobrenome;
	private String email;
	private Sexo sexo;
	private String senha;
	private Date dataNascimento;
	private String foto;

	public UsuarioCadastroDTO() {
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

}
