package br.edu.ifam.socialdesk.domain;

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

@Entity
@Table(name = "CHAMADO")
public class Chamado implements Serializable {

	private static final long serialVersionUID = 1L;

	public Chamado() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "DATA_CRIACAO")
	private Date dataCriacao;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "ID_CATEGORIA")
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "ID_STATUS")
	private Status status;

	@Column(name = "QUANTIDADE_CURTIDA")
	private Long quantidadeCurtida;

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Long getQuantidadeCurtida() {
		return quantidadeCurtida;
	}

	public void setQuantidadeCurtida(Long quantidadeCurtida) {
		this.quantidadeCurtida = quantidadeCurtida;
	}
}
