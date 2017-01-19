package it.umbriadigitale.rest.base;

import it.umbriadigitale.rest.dto.BaseRequest;
import it.umbriadigitale.rest.dto.BaseResponse;
import it.umbriadigitale.rest.service.BaseService;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.naming.NamingException;









import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;



public class BaseWs {


	protected Logger logger = Logger.getLogger("ud-rest-lib");
	// package dela classe si implementazione dei servizi 
	String pack;
	
	public BaseWs (String pack)  {
		this.pack = pack;
		
	}
	

	
	@GET
    @Path("/execute/{service}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response executeService(@PathParam("service") String service, @QueryParam("req") String req) {

		
		

		BaseResponse response = null;
		String fullPathServizio = pack.concat(".").concat(service);
		
		try {
			BaseService<BaseRequest, BaseResponse> servizio = ServiceFactory.build(fullPathServizio, req);
			
		} catch (Exception e) {
			logger.error("Errore reperimento servizio",e);
			return ResponseManager.build(e, "Errore reperimento servizio");
	
	
		}

		try {
			BaseService<BaseRequest, BaseResponse> servizio = ServiceFactory.build(fullPathServizio, req);
			
			response = servizio.execute();
			

    	
		} catch (Exception e) {
			logger.error("Errore chiamata servizio",e);
			
			return ResponseManager.build(e, "Errore chiamata servizio");
	
	
		}


		return  ResponseManager.build(Status.OK,response);
    	
    	
    	
        
    }
	
	
	
	

	
}
