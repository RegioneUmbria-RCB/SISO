package it.umbriadigitale.rest.base.responseMan;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
 
public class AppExceptionMapper implements ExceptionMapper<it.umbriadigitale.rest.base.responseMan.AppException> {
 

	public Response toResponse(AppException ex) {
		return Response.status(ex.getStatus())
				.entity(new ErrorMessage(ex))
				.type(MediaType.APPLICATION_JSON).
				build();
	}
	

	
	
}


