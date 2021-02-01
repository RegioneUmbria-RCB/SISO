package it.webred.cs.sociosan.web.rest.services;
import it.umbriadigitale.rest.service.BaseAuthService;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.umbriadigitale.rest.service.IAuthManager;
import it.webred.cs.csa.ejb.dto.rest.TrascodificheRequestDTO;
import it.webred.cs.csa.ejb.dto.rest.TrascodificheResponseDTO;
import it.webred.cs.sociosan.ejb.client.AccessTableConfigurazioneSessionBeanClientRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.cs.sociosan.web.rest.dto.TrascodificheRequest;
import it.webred.cs.sociosan.web.rest.dto.TrascodificheResponse;
import it.webred.ejb.utility.ClientUtility;

import javax.naming.NamingException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LetturaTabelleTrascodificaRestService  extends BaseAuthService<TrascodificheRequest, TrascodificheResponse> {

		public LetturaTabelleTrascodificaRestService(TrascodificheRequest req) {
			super(req);
		}

		public TrascodificheResponse executeAuthService() {
			TrascodificheResponse resp =  new TrascodificheResponse();
			TrascodificheRequestDTO input = new TrascodificheRequestDTO();
			
			AccessTableConfigurazioneSessionBeanClientRemote sb=null;
			try {
				sb = (AccessTableConfigurazioneSessionBeanClientRemote) 
						ClientUtility.getEjbInterface("SocioSanitario", "SocioSanitario_EJB", "AccessTableConfigurazioneSessionBeanClient");
			} catch (NamingException e1) {
				logger.error(e1);
			}

			try {
				
				input = (TrascodificheRequestDTO)req.getInput();
				 
				TrascodificheResponseDTO responseDTO =	sb.estraiTabellaConfigurazione(input);
				resp.setOutput(responseDTO);
			 //	DEBUG
 				ObjectMapper objectMapper = new ObjectMapper();
	        	String	json = objectMapper.writeValueAsString(resp);
				
				
			} catch (SocioSanitarioException e1) {
				logger.error(e1);

			}
			catch (Exception e2) {
				logger.error(e2);

			}
			return resp;
		}

		
		

		@Override
		public IAuthManager getAuthManager() {
			return new SocioSanFreeServiceAuthManager();
		}
		
		@Override
		public it.umbriadigitale.rest.service.BaseService.TipoServizioRest getTipoServizioRest()
				throws BaseServiceException {
			return TipoServizioRest.GET;
		}


	}








