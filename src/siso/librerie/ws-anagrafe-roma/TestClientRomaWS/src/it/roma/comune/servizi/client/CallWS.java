package it.roma.comune.servizi.client;

import it.roma.comune.servizi.test.ClientAnagrafeRomaStub;
import it.roma.comune.servizi.test.ClientAnagrafeRomaStub.*;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

 
public class CallWS {

	private String urlTomcat;
	private String token;
	
	public CallWS(String urlTomcat, String token){
		this.urlTomcat=urlTomcat;
		this.token = token;
	}
	public RicercaResult eseguiRicercaAnagraficaEstesa(String wsURL, String cognome, String nome, String annoIniziale, String annoFinale, String sesso  ) throws RemoteException{
		EseguiRicercaAnagraficaEstesa  esegRicAnagEstesa = new EseguiRicercaAnagraficaEstesa();
		esegRicAnagEstesa.setAnnoFinale(annoFinale);
		esegRicAnagEstesa.setAnnoIniziale(annoIniziale); 
		esegRicAnagEstesa.setCognome(cognome); 
		esegRicAnagEstesa.setNome(nome);
		esegRicAnagEstesa.setSesso(sesso);
		esegRicAnagEstesa.setWsURL(wsURL);
		esegRicAnagEstesa.setChiaveCifratura(token);
		
		ClientAnagrafeRomaStub clientAnaRoma = new ClientAnagrafeRomaStub(urlTomcat);
		EseguiRicercaAnagraficaEstesaResponse eseguiRicercaAnagResp = clientAnaRoma.eseguiRicercaAnagraficaEstesa(esegRicAnagEstesa);
		 
		RicercaResult rr = eseguiRicercaAnagResp.get_return();

/*		System.out.println(rr.getEsito());
		 Persona[]  arr = rr.getElencoPersona();
		
		for(Persona p :arr ){
			if(p.getDatiAnagrafeRoma() != null){
				System.out.println(p.getDatiAnagrafeRoma().getCodiceIndividuale());
				
			}
			if(p.getPersonaCompleta()  != null){
				System.out.println(p.getPersonaCompleta().getNome());
				System.out.println(p.getPersonaCompleta().getCognome());
				System.out.println(p.getPersonaCompleta().getCodiceFiscale());
			}
		}
		*/
		return rr;
	}
	
	public RicercaResult ricercaPerCodiceIndividuale(String wsURL, String id  ) throws RemoteException{
		RicercaPerCodiceIndividuale ricerca = new RicercaPerCodiceIndividuale();
		ricerca.setCodiceIndividuale(id);
		ricerca.setWsURL(wsURL);
		ricerca.setChiaveCifratura(token);
		
		ClientAnagrafeRomaStub clientAnaRoma = new ClientAnagrafeRomaStub(urlTomcat);
		RicercaPerCodiceIndividualeResponse response = clientAnaRoma.ricercaPerCodiceIndividuale(ricerca);
		RicercaResult rr = response.get_return();

	/*	System.out.println(rr.getEsito());
		 Persona[]  arr = rr.getElencoPersona();
		
		for(Persona p :arr ){
			if(p.getDatiAnagrafeRoma() != null){
				System.out.println(p.getDatiAnagrafeRoma().getCodiceIndividuale());
				
			}
			if(p.getPersonaCompleta()  != null){
				System.out.println(p.getPersonaCompleta().getNome());
				System.out.println(p.getPersonaCompleta().getCognome());
				System.out.println(p.getPersonaCompleta().getCodiceFiscale());
			}
		}*/
		return rr;
	}
	
	private it.roma.comune.servizi.dto.xsd.RicercaResult convertStubToXSDDto(it.roma.comune.servizi.test.ClientAnagrafeRomaStub.RicercaResult src){
		
		it.roma.comune.servizi.dto.xsd.RicercaResult dest = null;
		try {
			if(src != null){
				dest = new it.roma.comune.servizi.dto.xsd.RicercaResult();
				dest.setEsito(src.getEsito());
				if(src.getElencoPersona() != null){
					List<it.roma.comune.servizi.dto.xsd.Persona> lPersona = new ArrayList<it.roma.comune.servizi.dto.xsd.Persona>();
					for(Persona p : src.getElencoPersona()){
						it.roma.comune.servizi.dto.xsd.Persona xsdP = new it.roma.comune.servizi.dto.xsd.Persona();
						
						   if(p.getDatiAnagrafeRoma() != null){
							   it.roma.comune.servizi.dto.xsd.DatiAnagrafeRoma xsdDaPersona = null;
							   PropertyUtils.copyProperties( xsdDaPersona,p.getDatiAnagrafeRoma());
							   xsdP.setDatiAnagrafeRoma(xsdDaPersona);
						   }
						   if(p.getDatiDiNascita() != null){
							   it.roma.comune.servizi.dto.xsd.Nascita xsdNascita = null;
							   PropertyUtils.copyProperties( xsdNascita,p.getDatiDiNascita());
							   xsdP.setDatiDiNascita(xsdNascita);
						   }
						   if(p.getDatiIndirizzo()  != null){
							   it.roma.comune.servizi.dto.xsd.DatiIndirizzo xsdDatiIndirizzo = null;
							   PropertyUtils.copyProperties( xsdDatiIndirizzo,p.getDatiIndirizzo());
							   xsdP.setDatiIndirizzo(xsdDatiIndirizzo);
						   }
						   if(p.getPersonaCompleta()  != null){
							   
							   it.roma.comune.servizi.dto.xsd.PersonaCompleta xsdPersonaCompleta= null;
							    
							   PropertyUtils.copyProperties( xsdPersonaCompleta,p.getPersonaCompleta());
							   xsdP.setPersonaCompleta(xsdPersonaCompleta);
						   }  
							
							lPersona.add(xsdP);
					}
					it.roma.comune.servizi.dto.xsd.Persona[] arrPersona = new it.roma.comune.servizi.dto.xsd.Persona[lPersona.size()];
					dest.setElencoPersona(lPersona.toArray(arrPersona));
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dest;
	}
	
	public RicercaResult eseguiRicercaAnagrafica(String wsURL, String nome, String cognome, String annoNascita,String meseNascita, String giornoNascita, String codiceFiscale) throws RemoteException{

		VerificaDatiAnagrafici verificaDatiAnagrafici = new VerificaDatiAnagrafici();
		verificaDatiAnagrafici.setAnnoNascita(annoNascita);
		verificaDatiAnagrafici.setCodiceFiscale(codiceFiscale);
		verificaDatiAnagrafici.setCognome(cognome);
		verificaDatiAnagrafici.setGiornoNascita(giornoNascita);
		verificaDatiAnagrafici.setMeseNascita(meseNascita);
		verificaDatiAnagrafici.setNome(nome);
		verificaDatiAnagrafici.setURL(wsURL);
		verificaDatiAnagrafici.setChiaveCifratura(token);

		ClientAnagrafeRomaStub clientAnaRoma = new ClientAnagrafeRomaStub(urlTomcat );

		VerificaDatiAnagraficiResponse verificaDatiAnagResp = clientAnaRoma.verificaDatiAnagrafici(verificaDatiAnagrafici);

		RicercaResult rr = verificaDatiAnagResp.get_return();

	/*	System.out.println(rr.getEsito());
		Persona[] arr = rr.getElencoPersona();

		for(Persona p :arr ){
		if(p.getDatiAnagrafeRoma() != null){
		System.out.println(p.getDatiAnagrafeRoma().getCodiceIndividuale());

		}
		if(p.getPersonaCompleta() != null){
		System.out.println(p.getPersonaCompleta().getNome());
		System.out.println(p.getPersonaCompleta().getCognome());
		System.out.println(p.getPersonaCompleta().getCodiceFiscale());
		}
		}*/
		return rr;
	}	

}
