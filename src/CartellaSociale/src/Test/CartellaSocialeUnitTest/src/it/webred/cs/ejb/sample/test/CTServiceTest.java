package it.webred.cs.ejb.sample.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URL;
import java.util.List;

import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.jsf.manbean.exception.CsUiCompException;
import it.webred.cs.sociosan.ejb.client.AtlanteSessionBeanRemote;
import it.webred.cs.sociosan.ejb.dto.ServiziDTO;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ct.data.access.aggregator.isee.IseeService;
import it.webred.ct.data.access.aggregator.isee.dto.InfoIseeDTO;
import it.webred.ct.data.access.aggregator.isee.dto.IseeDataIn;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.client.anag.client.AnagrafeClient;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.siso.ws.client.anag.client.RicercaAnagraficaBean;
import it.webred.siso.ws.client.anag.model.SiancPazientePazienteBean;
import it.webred.siso.ws.client.atlante.client.AtlanteClient;
import it.webred.siso.ws.client.atlante.client.dto.GetServiziOspiteDTO;
import it.webred.siso.ws.client.atlante.exception.AtlanteException;
import it.webred.utils.GenericTuples.T2;

import javax.naming.NamingException;

import org.junit.Test;

public class CTServiceTest {

	
	@Test
	public void getInfoIsee() throws CsUiCompException  {
		try {
			
	    	    IseeService ejb = ClientUtility.getEJBInterfaceForRemoteClient(IseeService.class , "CT_Service", "CT_Service_Data_Access", "IseeServiceBean", "");


	    	    
	    	    IseeDataIn dataIn = new IseeDataIn();
	    	    dataIn.setCodiceFiscale("PLLNNN46L22F205J");
	    	    fillEnte(dataIn,"B212");
				InfoIseeDTO test = ejb.getInfoIsee(dataIn);

				assertNotNull(test.getEnteId());
				
		} catch (NamingException e) {
			e.printStackTrace();

		}
				
	}
	
	
	
	

	    
	
	public static CeTBaseObject fillEnte(CeTBaseObject ro, String ente) {	
		
		ro.setEnteId(ente);
		
		return ro;
	}
	
    private static AccessTableSoggettoSessionBeanRemote lookupRemoteEJB() throws NamingException {

    	
    	AccessTableSoggettoSessionBeanRemote ejb = ClientUtility.getEJBInterfaceForRemoteClient(AccessTableSoggettoSessionBeanRemote.class , "CT_Service", "CT_Service_EJB", "IseeServiceBean", "");
    	
    	return ejb;
    	 
    }
	    		


}
