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

public class AccessDataTableAppClientTest {

	
	@Test
	public void getServiziViaJar() throws Exception {
		// produzione  AtlanteClient atlante = new AtlanteClient( new URL ("http://172.29.0.234:8281/services/SISOService?wsdl"));
		// test 
		AtlanteClient atlante = new AtlanteClient( new URL ("http://172.29.0.178:8281/services/SISOService?wsdl"));
		
		
		atlante.login("USISO", "SISO2015" );
		List<GetServiziOspiteDTO> servizi = atlante.getServiziOspite("GSALGN36M59D842F");
		
		assertNotNull(servizi);
	}
	
	
	@Test
	public void getAnagrafeViaJar() throws Exception {
		AnagrafeClient anag = new AnagrafeClient( new URL ("http://172.29.0.234:8281/services/SISOService?wsdl"));
		
		
		RicercaAnagraficaBean rb= new RicercaAnagraficaBean();
		rb.setUsername("angelo.celoni");
		rb.setPassword("angelo2015");
//		rb.setEntita( "FIND_PAZ");
		anag.openSession(rb);
		rb.setCodiceFiscale("DLNNGL74D15D653R");
		SiancPazientePazienteBean paziente=anag.get(rb);
		paziente=anag.get(rb);
		System.out.print(paziente);
		
		rb= new RicercaAnagraficaBean();
		rb.setCognomePaziente("ranucci");
		rb.setNomePaziente("");
		List <PersonaFindResult> listaPersone=anag.findCognome(rb);
//		List <PersonaFindResult> listaPersone=anag.findCognomeNome(rb);
		anag.closeSession();
		
		
		assertNotNull(listaPersone);
	}
	

	
	
	@Test
	public void getServiziSanitari() throws CsUiCompException  {
		try {
				AtlanteSessionBeanRemote bean = ClientUtility.getEJBInterfaceForSTANDALONERemoteClient(AtlanteSessionBeanRemote.class ,"SocioSanitario", "SocioSanitario_EJB", "AtlanteSessionBean","");
				ServiziDTO s1 = bean.getServizi("GSALGN36M59D842F");
				ServiziDTO s2 = bean.getServizi("MBRLGN31A67C117O");
				
				
				
				
				
				assertNotNull(s1);
				assertNotNull(s2);
				
		} catch (SocioSanitarioException e) {
			
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();

		}

				
				
	}
	
	
	
	
	 @Test
	    public void testLookup() {
		    try {
		    	AccessTableSoggettoSessionBeanRemote bean = lookupRemoteEJB();
		    	assertEquals("sss", "sss");   
				
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	  

	    
	
	@Test
	public void testGetTableData() {
	    try {
	    	AccessTableSoggettoSessionBeanRemote bean = lookupRemoteEJB();
	    	
	    	BaseDTO b = new BaseDTO();
			fillEnte(b);
			b.setObj("CPRMRA50A01F704P");

				
			it.webred.cs.data.model.CsASoggettoLAZY lista = (it.webred.cs.data.model.CsASoggettoLAZY)bean.getSoggettoByCF(b);
			assertNotNull(lista);
				
				
		} catch (NamingException e) {
			fail(e.getMessage());
		}
	}
	
	
	public static CeTBaseObject fillEnte(CeTBaseObject ro) {	
		
		ro.setEnteId("G148");
		
		return ro;
	}
	
    private static AccessTableSoggettoSessionBeanRemote lookupRemoteEJB() throws NamingException {

    	
    	AccessTableSoggettoSessionBeanRemote ejb = ClientUtility.getEJBInterfaceForSTANDALONERemoteClient(AccessTableSoggettoSessionBeanRemote.class , "CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean", "");
    	
    	return ejb;
    	 
    }
	    		


}
