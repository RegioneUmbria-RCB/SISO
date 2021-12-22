package it.umbriadigitale.soclav.rest;

import java.net.URISyntaxException;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
 

/**
 * A stateless EJB bean to handle REST requests to the messages resource.
 * Messages are stored in the injected EJB singleton instance.
 *
 * @author Pavel Bucek
 */
@Path("/AnagraficaGepi")
@Stateless
public class AnagraficaGepiRestBean {

    @Context
    private UriInfo ui;
    
    protected static Logger logger = Logger.getLogger(AnagraficaGepiRestBean.class);

     
    @Path("/getAnagraficheEnte/{codEnte}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Messaggio getMessages(@PathParam("codEnte") String codEnte, @HeaderParam("authorization") String authString) {
    	
    	Messaggio mess = null;
    	 
        if(!isUserAuthenticated(authString)){
            return new Messaggio("-1001", "Credenziali di accesso non valide!");
        }
    	if(codEnte == null) {
    		mess = new Messaggio("-1002", "Parametro in input assente [codEnte]");
    		return mess;
    	}
    	RdcAnagraficaGepiInterface rdcAnagraficaGepi = (RdcAnagraficaGepiInterface) getEjb(
    			"SocLav", "SocLav_WEB", "RdcAnagraficaGepiBean");
    	 
        return rdcAnagraficaGepi.getAnagraficheByCodEnte(codEnte);
    }

    @POST
    public Response addMessage(String msg) throws URISyntaxException {
//        Message m = singleton.addMessage(msg);
//
//        URI msgURI = ui.getRequestUriBuilder().path(Integer.toString(m.getUniqueId())).build();

        return Response.created(ui.getRequestUriBuilder().build()).build();
    }

    @Path("{msgNum}")
    @GET
    public Messaggio getMessage(@PathParam("msgNum") int msgNum) {
//        Message m = singleton.getMessage(msgNum);
//
//        if (m == null) {
//            // This exception will be passed through to the JAX-RS runtime
//            // No other runtime exception will behave this way unless the
//            // exception is annotated with javax.ejb.ApplicationException
//            throw new NotFoundException();
//        }
//
//        return m;
    	return null;

    }

    @Path("{msgNum}")
    @DELETE
    public void deleteMessage(@PathParam("msgNum") int msgNum)  {
      //  boolean deleted = singleton.deleteMessage(msgNum);
    	 
       
    }
    public Object getEjb(String ear, String module, String ejbName){
    	javax.naming.Context cont;
		try {
			cont = new InitialContext();
			return cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
    
    private boolean isUserAuthenticated(String authString){
        
        String decodedAuth = "";
        // Header is in the format "Basic 5tyc0uiDat4"
        // We need to extract data before decoding it back to original string
        String[] authParts = authString.split("\\s+");
        String authInfo = authParts[1];
        // Decode the data back to original string
        byte[] bytes = null;
        try {
            bytes = java.util.Base64.getDecoder().decode(authInfo);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        decodedAuth = new String(bytes);
        System.out.println(decodedAuth);
         
         
        // your validation code goes here....
         
        return true;
    }
}

