package it.webred.siso.ws.client.atlante.client;


import it.webred.siso.ws.client.atlante.client.dto.GetServiziOspiteDTO;
import it.webred.siso.ws.client.atlante.exception.AtlanteException;
import it.webred.siso.ws.client.atlante.exception.AtlanteLoginNonValidaException;
import it.webred.siso.ws.client.atlante.exception.AtlanteNessunServizioException;
import it.webred.siso.ws.client.atlante.exception.AtlanteOspiteNonTrovatoException;
import it.webred.siso.ws.client.atlante.model.GetServiziOspite.request.GetServiziOspite;
import it.webred.siso.ws.client.atlante.model.GetServiziOspite.request.Request;
import it.webred.siso.ws.client.atlante.model.GetServiziOspite.response.GetServiziOspiteResponse;
import it.webred.siso.ws.client.atlante.model.GetServiziOspite.response.ServiziOspite.ServizioOspiteResponse;
import it.webred.siso.ws.client.atlante.model.Login.request.Login;
import it.webred.siso.ws.client.atlante.model.Login.response.LoginResponse;
import it.webred.siso.ws.client.client.SisoWSClient;
import it.webred.siso.ws.client.client.exception.FaultResponseException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.beanutils.BeanUtils;


public class AtlanteClient extends SisoWSClient {

	URL urlWSDL;
	private String token;
	
	
	public AtlanteClient(URL urlWSDL) throws AtlanteException {
		try {
			this.urlWSDL = urlWSDL;
			
		} catch (Exception e) {
			throw new AtlanteException(e);
		}
	}

	@Override
	protected com.sun.xml.bind.marshaller.NamespacePrefixMapper getNamespacePrefixMapper() {
		
		return new AtlanteClient.MyAtlanteNamespaceMapper();
	}

	public void  login(String user, String password)
			throws AtlanteException {
		try {

			LoginContext context = new LoginContext(urlWSDL);
			Login logi = new Login();
			Login.Request req = new Login.Request();
			logi.setRequest(req);
			req.setPassword(password);
			req.setUsername(user);

			context.setInput(logi);

			execute(context);
			
			
			LoginResponse loginResponse = (LoginResponse) context.getOutput();
			
			if ("".equals(loginResponse.getLoginResult().getToken()))
				throw new AtlanteLoginNonValidaException();
				
						
			this.token =  loginResponse.getLoginResult().getToken();
	
		} catch (Exception e) {
			throw new AtlanteException(e);
		}

	}
	
	public List<GetServiziOspiteDTO>  getServiziOspite(String codiceFiscale)
			throws AtlanteNessunServizioException,AtlanteOspiteNonTrovatoException,AtlanteException {
		try {
			
			List<GetServiziOspiteDTO> serviziOspite  = new ArrayList<GetServiziOspiteDTO>();

			if (this.token==null)
					throw new AtlanteException("Effettuare login perima di chiamare il servizio");
			
			ServiziOspiteContext context = new ServiziOspiteContext(urlWSDL);
			Request req = new Request();
			req.setCodiceFiscale(codiceFiscale);
			req.setToken(token);
//			req.setAData("31/12/9999");

			GetServiziOspite payload = new GetServiziOspite();
			payload.setRequest(req);
			context.setInput(payload);

			execute(context);
		
			GetServiziOspiteResponse getServizioOspiteResponse = (GetServiziOspiteResponse) context.getOutput();
			
			try {
				List<ServizioOspiteResponse> serviziOspiteResponse  = new ArrayList<ServizioOspiteResponse>();
				serviziOspiteResponse = getServizioOspiteResponse.getGetServiziOspiteResult().getServiziOspite().getServizioOspiteResponse();
				Iterator iterator = serviziOspiteResponse.iterator(); 
				while (iterator.hasNext()) {
					ServizioOspiteResponse servizioOspiteResponse = (ServizioOspiteResponse) iterator.next();
					GetServiziOspiteDTO serviziOspiteDTO = new GetServiziOspiteDTO();
					
					BeanUtils.copyProperties(serviziOspiteDTO, servizioOspiteResponse);
					serviziOspite.add(serviziOspiteDTO);
				}

				
				return serviziOspite;
			} catch (NullPointerException ne) {
				// nessun servizio
				throw new AtlanteNessunServizioException();
			}
	
		} catch (SOAPFaultException e){
			throw new AtlanteOspiteNonTrovatoException(e.getMessage());
		}catch (FaultResponseException e) {
			if (e.getMessage()!=null && e.getMessage().contains("stato trovato"))
				throw new AtlanteOspiteNonTrovatoException(e.getMessage());
			else
				throw new AtlanteException(e);
		}  catch (Exception e) {
			throw new AtlanteException(e);
		}

	}
	
	/**
	 * Questa classe viene utilizzata per il mapping dei prefissi dei nodi xml del payload
	 * Nel bean jaxb (Login, Request ..) per ogni XmlElement deve essere specificato il namespace.
	 * La classe legge il namespace e mette il prefisso corretto al nodo.
	 * @author marcoultra
	 *
	 */
														 
	public static class MyAtlanteNamespaceMapper extends com.sun.xml.bind.marshaller.NamespacePrefixMapper  {
		private static final String GES_PREFIX = "ges"; // DEFAULT NAMESPACE
	    private static final String GES_URI = "http://sistematlante.it/gestioneServizi";

	    private static final String ATL_PREFIX = "atl";
	    private static final String ATL_URI = "http://sistematlante.it/AtlanteWebServices.ObjectModel.Messages";

	    private static final String ATL_PREFIX_GETSERVIZI_OSPITE = "atl";
	    private static final String ATL_URI_GETSERVIZIO_OSPITE = "http://schemas.datacontract.org/2004/07/AtlanteWebServices.ObjectModel.Messages.GestioneServizi";
	    
	      	
	    @Override
	    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
	        if(GES_URI.equals(namespaceUri)) {
	            return GES_PREFIX;
	        } else if(ATL_URI.equals(namespaceUri)) {
	            return ATL_PREFIX;
	        } else if(ATL_URI_GETSERVIZIO_OSPITE.equals(namespaceUri)) {
	            return ATL_PREFIX_GETSERVIZI_OSPITE;
	        }
	        return suggestion;
	    }

	    @Override
	    public String[] getPreDeclaredNamespaceUris() {
	        return new String[] { GES_PREFIX, ATL_PREFIX, ATL_PREFIX_GETSERVIZI_OSPITE };
	    }
	}
	
	


}
