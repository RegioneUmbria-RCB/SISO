package it.roma.comune.servizi.client;

import it.roma.comune.servizi.test.*;
import it.roma.comune.servizi.dto.*;

import java.rmi.RemoteException;

public class CallWS {

	private String urlTomcat;
	private String token;
	
	public CallWS(String urlTomcat, String token){
		this.urlTomcat=urlTomcat;
		this.token = token;
	}
	public RicercaResult eseguiRicercaAnagraficaEstesa(String wsURL, String cognome, String nome, String annoIniziale, String annoFinale, String sesso  ) throws RemoteException{
		
		System.out.println("eseguiRicercaAnagraficaEstesa"+"["+urlTomcat+"]["+token+"]");
		System.out.println("["+cognome+"]["+nome+"]["+sesso+"]["+annoIniziale+"]["+annoFinale+"]");
		
 	
		ClientAnagrafeRomaProxy clientAnaRoma = new ClientAnagrafeRomaProxy(urlTomcat);
		RicercaResult rr = clientAnaRoma.eseguiRicercaAnagraficaEstesa(token,wsURL, cognome, nome, sesso, annoIniziale, annoFinale );
 
	 
		return rr;
	}
	
	public RicercaResult ricercaPerCodiceIndividuale(String wsURL, String id  ) throws RemoteException{
		
		System.out.println("ricercaPerCodiceIndividuale"+"["+id+"]["+urlTomcat+"]["+token+"]");
	 
		ClientAnagrafeRomaProxy clientAnaRoma = new ClientAnagrafeRomaProxy(urlTomcat);
		RicercaResult rr =  clientAnaRoma.ricercaPerCodiceIndividuale(token, wsURL, id);
	 
		return rr;
	}
	
	public RicercaResult eseguiRicercaAnagrafica(String wsURL, String nome, String cognome, String annoNascita,String meseNascita, String giornoNascita, String codiceFiscale) throws RemoteException{

		 
		System.out.println("eseguiRicercaAnagrafica"+"["+urlTomcat+"]["+token+"]");
		System.out.println("["+cognome+"]["+nome+"]["+annoNascita+"]["+meseNascita+"]["+giornoNascita+"]["+codiceFiscale+"]");
		
 
		 
		ClientAnagrafeRomaProxy clientAnaRoma = new ClientAnagrafeRomaProxy( urlTomcat);
		 
		RicercaResult rr = clientAnaRoma.verificaDatiAnagrafici(token,wsURL, cognome, nome, "-", annoNascita, meseNascita, giornoNascita, codiceFiscale );

		 
	 
		return rr;
	}	
	public RicercaResult eseguiRicercaStatoFamigliaConv(String wsURL, String codiceIndividuale, String codiceFiscale) throws RemoteException{

		 
		System.out.println("eseguiRicercaStatoFamigliaConv "
				+ "[codIndividuale:"+codiceIndividuale != null ? codiceIndividuale : "-"+"]["+codiceFiscale != null ? codiceFiscale : "-"+"]"
				+ "["+urlTomcat+"]["+token+"]");
		
		ClientAnagrafeRomaProxy clientAnaRoma = new ClientAnagrafeRomaProxy( urlTomcat);
		 
		RicercaResult rr = clientAnaRoma.eseguiRicercaStatoFamigliaConv(token, wsURL, codiceIndividuale, codiceFiscale);

		 
	 
		return rr;
	}	
}
