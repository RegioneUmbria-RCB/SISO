package it.umbriadigitale.rest.base.responseMan;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseManager {

	
	public static Response build(Exception e, String userMessage) {
		AppException ex = new AppException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), 0, userMessage, e.getMessage(), null);
		return new AppExceptionMapper().toResponse(ex);

	}

	public static Response build(Status status, Object entity ) {
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(entity);
		} catch (JsonProcessingException e) {
			return build(e,"Errore imprevisto");

		}
		return Response.status(status.getStatusCode()).entity(json).build();
		
	}

	

	
}
