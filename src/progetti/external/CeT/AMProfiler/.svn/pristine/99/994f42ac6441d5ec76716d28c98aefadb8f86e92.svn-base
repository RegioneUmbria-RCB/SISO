package it.webred.AMProfiler.rest.services;

import it.webred.AMProfiler.parameters.AmProfilerBaseBean;
import it.webred.AMProfiler.rest.services.dto.LoginDTO;
import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.ct.support.validation.CeTToken;
import it.webred.utils.GenericTuples.T2;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
 


@Path("login")
public class LoginResource extends AmProfilerBaseBean {
	
	 
	
	  

	LoginBeanService loginService = (LoginBeanService) getEjb(
			"AmProfiler", "AmProfilerEjb", "LoginBean");
	
	
 
    
	
	
    @Path("access")
    @Produces({MediaType.APPLICATION_JSON})
    @GET
    public javax.ws.rs.core.Response access(@HeaderParam("req") String login) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	LoginDTO loginDTO;
		try {
			loginDTO = objectMapper.readValue(login, LoginDTO.class);
		} catch (JsonParseException e1) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (JsonMappingException e1) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (IOException e1) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
    	

    	CeTToken t = loginService.getToken(loginDTO.getUserName(), loginDTO.getPwd(), loginDTO.getEnte());
    	String omToken=null;
		try {
			omToken = objectMapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
    	return Response.status(200).entity(omToken).build();
        
    }
 
  
    
    @Path("getEntiByUtente")
    @Produces({MediaType.APPLICATION_JSON})
    @GET
    public javax.ws.rs.core.Response getEntiByUtente(@QueryParam("u") String username) {
    	ObjectMapper objectMapper = new ObjectMapper();

    	List<T2<String, String>> enti = loginService.getEntiByUtente(username);
    	
    	String entiJson = null;
		try {
			entiJson = objectMapper.writeValueAsString(enti);
		} catch (JsonProcessingException e) {
			Response.status(500);
		}
    	return Response.status(200).entity(entiJson).build();
        
    }
    
}