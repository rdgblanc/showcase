package br.com.vitrinedecristal.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.security.access.AccessDeniedException;

import br.com.vitrinedecristal.swagger.ApiExceptionResponse;
import br.com.vitrinedecristal.swagger.AuthorizationException;

@Provider
public class AuthorizationExceptionMapper implements ExceptionMapper<AccessDeniedException> {

	public Response toResponse(AccessDeniedException exception) {
		return Response.status(Status.FORBIDDEN).entity(new ApiExceptionResponse(AuthorizationException.MESSAGE)).build();
	}

}