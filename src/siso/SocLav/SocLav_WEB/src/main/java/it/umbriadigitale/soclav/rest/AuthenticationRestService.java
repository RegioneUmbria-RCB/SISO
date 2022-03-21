package it.umbriadigitale.soclav.rest;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import it.umbriadigitale.soclav.util.Token;

@Path("/AuthService")
public class AuthenticationRestService {

	private static final Logger logger = Logger.getLogger(AuthenticationRestService.class);

	@Context
    private HttpServletRequest request;
    @Context 
    private HttpServletResponse response;
    
	@GET
	@Path("/testToken")
    @Produces(MediaType.TEXT_PLAIN)
	public String testToken() {

		String response = null;
		Token token = new Token();
        try{			
          String tokenEncoded =  token.get("login", "CPRMRA50A01F704P");
          Thread.sleep(3100);
          response = token.verify(tokenEncoded).getMessaggio(); 
          
        }
        catch(Exception e){
        	response = e.getMessage().toString();
        }
        
        if(logger.isDebugEnabled()){
            logger.debug("result: '"+response+"'");
            logger.debug("End getSomething");
        }
        return response;	
	}

	@SuppressWarnings("finally")
	@GET
	@Path("/login")
	//@Produces(MediaType.TEXT_HTML)
	  public Response performLogin(@HeaderParam("token")  String token ) {
		 URI uri =  URI.create("http://localhost:8080/SocLav_WEB/login.xhtml");   
		try {
		     
		     
				/**
				 * 	String response = null;
						Token token = new Token();
				        try{			
				          String tokenEncoded =  token.get("login", "CPRMRA50A01F704P");
				          Thread.sleep(3100);
				          response = token.verify(tokenEncoded).getMessaggio(); 
				          
				        }
				        catch(Exception e){
				        	response = e.getMessage().toString();
				        }
				        
				 * **/
		      
		      //URI authenticationUrl = URI.create(requestToken.getAuthenticationURL());

		      //return Response.temporaryRedirect(uri).build();
			  String contextPath = request.getContextPath();
	          response.sendRedirect(contextPath + "/login.xhtml");
			  Response.status(Response.Status.SEE_OTHER);
		      //return Response.seeOther(uri).build();
			  return Response.status(Response.Status.ACCEPTED).build();
		     
		    }  catch (Exception e) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}  finally {
				 return Response.temporaryRedirect(uri).build();
			}
		  }
}
