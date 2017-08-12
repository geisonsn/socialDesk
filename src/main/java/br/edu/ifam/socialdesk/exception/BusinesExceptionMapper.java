package br.edu.ifam.socialdesk.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BusinesExceptionMapper implements ExceptionMapper<BusinessException> {

	@Override
	public Response toResponse(BusinessException be) {

		return Response.status(be.getStatus()).entity(be.getEntity()).build();
	}

}
