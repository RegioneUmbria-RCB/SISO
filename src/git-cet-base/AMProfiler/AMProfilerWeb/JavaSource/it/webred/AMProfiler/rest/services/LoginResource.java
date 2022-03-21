package it.webred.AMProfiler.rest.services;

import it.webred.AMProfiler.parameters.AmProfilerBaseBean;
import it.webred.AMProfiler.rest.services.dto.LoginDTO;
import it.webred.AMProfiler.util.EsitoAut;
import it.webred.amprofiler.ejb.dto.AmBaseDTO;
import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.amprofiler.ejb.perm.LoginServiceException;
import it.webred.amprofiler.model.AmTracciaAccessi;
import it.webred.ct.support.validation.CeTToken;
import it.webred.utils.GenericTuples.T2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
	
	LoginBeanService loginService = (LoginBeanService) getEjb("AmProfiler", "AmProfilerEjb", "LoginBean");
	
    @Path("access")
    @Produces({MediaType.APPLICATION_JSON})
    @GET
    public javax.ws.rs.core.Response access(@HeaderParam("req") String login) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	//logger.info("Login Resource - login["+login+"]");
    	
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
    	
    		
    	
    	try{
    		CeTToken t = loginService.getToken(loginDTO.getUserName(), loginDTO.getPwd(), loginDTO.getEnte());
        	logger.info("Login Resource - username["+loginDTO.getUserName()+"], ente["+loginDTO.getEnte()+"]");
        	
		
	    	AmTracciaAccessi traccia = new AmTracciaAccessi();
	    	traccia.setEnte(loginDTO.getEnte());
	    	traccia.setUserName(loginDTO.getUserName());
	    	traccia.setRagioneAccesso("Accesso da Web Service");
	    	traccia.setFkAmItem("AMProfiler");
	    	traccia.setPratica("-");
	    	traccia.setSessionId(t.getSessionId());
	    	
	    	AmBaseDTO dto = new AmBaseDTO();
	    	dto.setEnteId(loginDTO.getEnte());
	    	dto.setUserId(loginDTO.getUserName());
	    	dto.setSessionId(t.getSessionId());
	    	dto.setObj1(traccia);
	
	    	loginService.salvaTracciaAccessi(dto);
	    	
	    	String omToken=null;
			
			omToken = objectMapper.writeValueAsString(t);
			
			return Response.status(200).entity(omToken).build();
		
    	} catch (LoginServiceException le) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	     catch (JsonProcessingException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
    	catch(Exception e){
    		if (e.getCause().getClass()== LoginServiceException.class)
    		{
    			return Response.status(Response.Status.UNAUTHORIZED).build();
    		}
    		else if (e.getCause().getClass()== JsonProcessingException.class) {
    			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    		}
    		else
    		{
    			logger.error("Errore salvataggio traccia accessi da WEB SERVICE", e);
        		return null;
    			
    		}
    			
    		
    	}
    	
    	
    	
        
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
    
    //SISO-SOCLAV
    @Path("getPermessiUtente")
    @Produces({MediaType.APPLICATION_JSON})
    @GET
    public javax.ws.rs.core.Response getPermissionByUtente(@QueryParam("u") String username, @QueryParam("e") String ente) {
    	ObjectMapper objectMapper = new ObjectMapper();

    	HashMap<String, String> permessiUtente = loginService.getPermissions(username, ente);
    	
    	String permessiUtenteJson = null;
		try {
			permessiUtenteJson = objectMapper.writeValueAsString(permessiUtente);
		} catch (JsonProcessingException e) {
			Response.status(500);
		}
    	return Response.status(200).entity(permessiUtenteJson).build();
        
    }
    //SISO-SOCLAV
    @Path("verificaPriK")
    @Produces({MediaType.APPLICATION_JSON})
    @GET
    public javax.ws.rs.core.Response verificaPriK(@QueryParam("priK") String priK) {
    	 
    	AmTracciaAccessi traccia = loginService.findTracciaAccessiByPriK(priK); 
    	EsitoAut esitoAut = new EsitoAut();
    	String esitoAutJson = null;
    	ObjectMapper objectMapper = new ObjectMapper();
		try {
			if(traccia == null) {
				//Confezionamento del messaggio traccia non trovata...
				esitoAut.setEsito(-1000);
				esitoAut.setMessaggio("Nessun utente individuato con la chiave in input");
			}
			if(traccia.getUsata() != null && traccia.getUsata().contentEquals("SI")) {
				//Confezionamento del messaggio chiave gi� usata...
				esitoAut.setEsito(-1001);
				esitoAut.setMessaggio("Attenzione! Accesso non consentito, la chiave � stata gi� usata. Ripetere la procedura di accesso.");
			}
			else {
				//  messaggio OK 
				//aggiornamento tabella con USATA = TRUE
				traccia.setUsata("SI");
				
			 	AmBaseDTO dto = new AmBaseDTO();
		    	dto.setEnteId(traccia.getEnte());
		    	dto.setUserId(traccia.getUserName());
		    	dto.setSessionId(traccia.getSessionId());
		    	dto.setObj1(traccia);
				
				loginService.aggiornaTracciaAccessiByPriK(dto); 
			
				
			     esitoAut.setEsito(0);
			     esitoAut.setMessaggio("Chiave di accesso riconosciuta e valida, accesso consentito.");
			}
		} catch ( Exception e) {
			Response.status(500);
			esitoAut.setEsito(-1003);
			esitoAut.setMessaggio("Errore generico! Ripetere l'accesso. ");
			
		}
		try {
			esitoAutJson = objectMapper.writeValueAsString(esitoAut);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	return Response.status(200).entity(esitoAutJson).build();
        
    }
/*    private HashMap<String, String> getPermessiUtenteIstanza(String username, String ente, String istanza){
    	HashMap<String, String> permessiUtente = loginService.getPermissions(username, ente);
    	HashMap<String, String> permessiIstanza = new HashMap<String, String>();
    	Iterator<String> iter = permessiUtente.keySet().iterator();
    	String prefisso = "permission@-@" + istanza + "@-@";
    	while(iter.hasNext()) {
    		String permesso = iter.next();
    		if(permesso.startsWith(prefisso)){
    		    String sub = permesso.replaceFirst(prefisso, "");
    			permessiIstanza.put(sub, sub);
    		}
    	}
    	return permessiIstanza;
    }
    
    @Path("getPermessiUtenteSocLav")
    @Produces({MediaType.APPLICATION_JSON})
    @GET
    public javax.ws.rs.core.Response getPermissionSocLavByUtente(@QueryParam("u") String username, @QueryParam("e") String ente) {
    	ObjectMapper objectMapper = new ObjectMapper();

    	HashMap<String, String> permessiUtente = this.getPermessiUtenteIstanza(username, ente, "SocLav");
    	    	
    	String permessiUtenteJson = null;
		try {
			permessiUtenteJson = objectMapper.writeValueAsString(permessiUtente);
		} catch (JsonProcessingException e) {
			Response.status(500);
		}
    	return Response.status(200).entity(permessiUtenteJson).build();
        
    }
    
    */
    
}