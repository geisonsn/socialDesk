package br.edu.ifam.socialdesk.security;

import java.security.Principal;

import javax.inject.Inject;

import br.edu.ifam.socialdesk.business.UsuarioBC;
import br.edu.ifam.socialdesk.domain.Usuario;
import br.gov.frameworkdemoiselle.security.Credentials;
import br.gov.frameworkdemoiselle.security.InvalidCredentialsException;
import br.gov.frameworkdemoiselle.security.TokenAuthenticator;
import br.gov.frameworkdemoiselle.util.Beans;

public class AppAuthenticator extends TokenAuthenticator {

	@Inject
	private UsuarioBC usuarioBC;

	private static final long serialVersionUID = 1L;

	@Override
	protected Principal customAuthentication() throws Exception {
		final Credentials credentials = Beans.getReference(Credentials.class);
		final String username = credentials.getUsername();
		String password = credentials.getPassword();

		final Usuario usuario = usuarioBC.login(username, password);

		if (usuario == null) {
			throw new InvalidCredentialsException();
		}

		return new Principal() {
			@SuppressWarnings("unused")
			public String getEmail() {
				return usuario.getEmail();
			}

			@SuppressWarnings("unused")
			public String getNome() {
				return usuario.getNomeUsuario();
			}

			@SuppressWarnings("unused")
			public Long getId() {
				return usuario.getId();
			}

			@Override
			public String getName() {
				return null;
			}
		};

		// Principal user = null;
		// if (credentials.getPassword().equals("secret")) {
		// user = new Principal() {
		//
		// @Override
		// public String getName() {
		// return username;
		// }
		// };
		//
		// } else {
		// throw new InvalidCredentialsException();
		// }
		//
		// return user;
	}
}
