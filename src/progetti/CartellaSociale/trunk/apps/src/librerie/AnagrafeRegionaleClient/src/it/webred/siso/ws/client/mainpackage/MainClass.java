package it.webred.siso.ws.client.mainpackage;


import it.webred.siso.ws.client.anag.client.AnagrafeClient;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.siso.ws.client.anag.client.RicercaAnagraficaBean;
import it.webred.siso.ws.client.anag.exception.AnagrafeException;
import it.webred.siso.ws.client.anag.exception.AnagrafeSessionException;
import it.webred.siso.ws.client.anag.model.SiancPazientePazienteBean;
import it.webred.siso.ws.client.atlante.client.AtlanteClient;
import it.webred.siso.ws.client.atlante.exception.AtlanteException;
import it.webred.siso.ws.client.client.exception.SisoClientException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;



public class MainClass {

	/**
	 * @param args
	 * @throws AnagrafeException 
	 * @throws AnagrafeSessionException 
	 */
	public static void main(String[] args) {

	/*			
		try {
			testAnagrafe();
		} catch (AnagrafeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AnagrafeSessionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
*/	
		try {
			testAtlante();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		

	   
		 
	}
	
	private static void testAtlante() throws  AtlanteException  {
		AtlanteClient atlanteClient = null;
			try {
				try {
					// AMBIENTE DI TEST esb
					atlanteClient = new AtlanteClient(new URL("http://172.29.0.178:8281/services/SISOService?wsdl"));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SisoClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		 atlanteClient.login("USISO","SISO2015");
/*
  		 MBRLGN31A67C117O In carico presso la RP “Non ti scordar di me” [residenzialità]
 				 GSALGN36M59D842F In carico presso il PES Alviano [domiciliarità]
*/
		 atlanteClient.getServiziOspite("TDDLRT46M23G148D"); //GSALGN36M59D842F");
		 atlanteClient.getServiziOspite("MBRLGN31A67C117O");
		 atlanteClient.getServiziOspite("FITTIZIO");
		 
		 
		 //System.out.println(resp);
		 

	}
	
	
	
	private static void testAnagrafe() throws AnagrafeException, AnagrafeSessionException {
		AnagrafeClient anag = null;
		try {
			try {
				anag = new AnagrafeClient(new URL("http://esb.siso.webred.it:8281/services/SISOService?wsdl"));
			} catch (SisoClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RicercaAnagraficaBean rb= new RicercaAnagraficaBean();
		rb.setUsername("angelo.celoni");
		rb.setPassword("angelo2016");
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
	}

}
