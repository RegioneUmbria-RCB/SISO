package it.umbriadigitale.rest.base;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
 
public class AppExceptionMapper implements ExceptionMapper<it.umbriadigitale.rest.base.AppException> {
 
	public Response toResponse(it.umbriadigitale.rest.base.AppException ex) {
		return Response.status(ex.getStatus())
				.entity(new it.umbriadigitale.rest.base.ErrorMessage(ex))
				.type(MediaType.APPLICATION_JSON).
				build();
	}
	

	
	
  
}