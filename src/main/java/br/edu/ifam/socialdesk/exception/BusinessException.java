package br.edu.ifam.socialdesk.exception;

import javax.ws.rs.core.Response.Status;

import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.ResourceBundle;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private Status status;
	private Object entity;

	public BusinessException(Status status, String mensagem, Object data) {
		super(mensagem);
		final ResourceBundle bundle = Beans.getReference(ResourceBundle.class);

		final String msg = bundle.containsKey(mensagem) ? bundle.getString(mensagem) : mensagem;
		this.status = status;
		this.entity = new Body(msg, data);
	}

	public BusinessException(String msg) {
		this(msg, null);
	}

	public BusinessException(String msg, Object data) {
		this(Status.BAD_REQUEST, msg, data);
	}

	public BusinessException(Status status, String mensagem) {

		this(status, mensagem, null);
	}

	public static class Body {

		public String mensagem;
		private Object data;

		public Body(String mensagem, Object data) {
			super();
			this.mensagem = mensagem;
			this.data = data;
		}

		public Body(String mensagem) {
			this(null, mensagem);
		}

		public String getMensagem() {
			return mensagem;
		}

		public void setMensagem(String mensagem) {
			this.mensagem = mensagem;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

}
