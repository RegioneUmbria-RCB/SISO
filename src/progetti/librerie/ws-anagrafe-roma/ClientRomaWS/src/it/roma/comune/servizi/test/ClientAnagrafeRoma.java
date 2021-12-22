package it.roma.comune.servizi.test;


import it.roma.comune.servizi.client.RicercaAnagraficaClient;
import it.roma.comune.servizi.dto.RicercaResult;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Properties;
 

public class ClientAnagrafeRoma {
	 private  String fileName = null; 
	 private String chiaveCifratura = null;
	 
	public ClientAnagrafeRoma(){
		try{
		     Context ctx = new InitialContext();
		     Context env = (Context) ctx.lookup("java:comp/env");
		     fileName = (String) env.lookup("keyFile");
		     Properties properties = new Properties();
		     properties.load(new FileInputStream(new File(fileName)));
		     chiaveCifratura =  properties.getProperty("chiaveCifratura");
		}catch(Exception ex ){
			ex.printStackTrace();
		}
	}
	  
	public RicercaResult verificaDatiAnagrafici(String chiaveCifratura, String URL, String cognome, String nome, String sesso, String annoNascita, String meseNascita, String giornoNascita, String codiceFiscale){
			
			RicercaResult rs =  null;
			RicercaAnagraficaClient ricAnaClient = new RicercaAnagraficaClient();
		try {
			
	 	    if(!this.chiaveCifratura.equals(chiaveCifratura)){
	 	    	rs = new RicercaResult();
	 	    	rs.setEsito("KO - Chiave di cifratura errata! Impossibile accedere.");
	 	    }
	 	    else{
	 	    	rs =  ricAnaClient.eseguiRicercaAnagrafica(URL, 
	 	    			cognome, 
	 	    			nome, 
	 	    			sesso, 
	 	    			annoNascita, 
	 	    			meseNascita,
	 	    			giornoNascita, 
	 	    			codiceFiscale);
	 	    }
	
			return rs;
			
	 
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
}
	public RicercaResult verificaDatiAnagraficiCompleta(String chiaveCifratura, String URL, String cognome, String nome, String sesso, String annoNascita, String meseNascita, String giornoNascita, String codiceFiscale){
		
		RicercaResult rs =  null;
		RicercaAnagraficaClient ricAnaClient = new RicercaAnagraficaClient();
	try {
		
 	    if(!this.chiaveCifratura.equals(chiaveCifratura)){
 	    	rs = new RicercaResult();
 	    	rs.setEsito("KO - Chiave di cifratura errata! Impossibile accedere.");
 	    }
 	    else{
 	    	rs =  ricAnaClient.eseguiRicercaAnagrafica(URL, 
 	    			cognome, 
 	    			nome, 
 	    			sesso, 
 	    			annoNascita, 
 	    			meseNascita,
 	    			giornoNascita, 
 	    			codiceFiscale);
 	    }

		return rs;
		
 
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
	public RicercaResult eseguiRicercaAnagraficaEstesa(String chiaveCifratura, String wsURL,String cognome,String nome,String sesso,String annoIniziale,String annoFinale){
		RicercaResult rs =  null;
		RicercaAnagraficaClient ricAnaClient = new RicercaAnagraficaClient();
		try {
			
			 if(!this.chiaveCifratura.equals(chiaveCifratura)){
		 	    	rs = new RicercaResult();
		 	    	rs.setEsito("KO - Chiave di cifratura errata! Impossibile accedere.");
		 	    }
		 	    else{
		 	    	rs =  ricAnaClient.eseguiRicercaAnagraficaEstesa(wsURL, cognome, nome, sesso, annoIniziale, annoFinale);
		 	    }
			return rs;
			
	 
		
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
	public RicercaResult eseguiRicercaStatoFamigliaConv(String chiaveCifratura, String wsURL,String codiceIndividuale , String codiceFiscale){
		RicercaResult rs =  null;
		RicercaAnagraficaClient ricAnaClient = new RicercaAnagraficaClient();
		try {
			
			 if(!this.chiaveCifratura.equals(chiaveCifratura)){
		 	    	rs = new RicercaResult();
		 	    	rs.setEsito("KO - Chiave di cifratura errata! Impossibile accedere.");
		 	    }
		 	    else{
		 	    	rs =  ricAnaClient.verificaStatoFamigliaConv(wsURL, codiceIndividuale, codiceFiscale);
		 	    }
			return rs;
			
	 
		
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
  public RicercaResult ricercaPerCodiceIndividuale(String chiaveCifratura, String wsURL,String codiceIndividuale){
	  RicercaResult rs =  null;
		RicercaAnagraficaClient ricAnaClient = new RicercaAnagraficaClient();
		try {
			
			 if(!this.chiaveCifratura.equals(chiaveCifratura)){
		 	    	rs = new RicercaResult();
		 	    	rs.setEsito("KO - Chiave di cifratura errata! Impossibile accedere.");
		 	    }
		 	    else{
		 	    		rs =  ricAnaClient.ricercaPerCodiceIndividuale(wsURL, codiceIndividuale);
		 	    }
			return rs;
			
	 
		
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
//	public void verificaDatiAnagrafici(String URL, String cognome, String nome, String annoNascita, String meseNascita, String giornoNascita, String codiceFiscale){
//		
//		RicercaAnagraficaClientProxy  ricAnaClientProxy = new RicercaAnagraficaClientProxy();
//		try {
//			
////		    Object[] arrPersone = 	ricAnaClientProxy.eseguiRicercaAnagrafica(URL, cognome, nome, annoNascita, meseNascita, giornoNascita, codiceFiscale);
////		    String msgEsito = ricAnaClientProxy.getEsito();
//			  RicercaAnagraficaClient ricAnaClient = 	ricAnaClientProxy.getRicercaAnagraficaClient(); 
//		    
//			 Object[] arrAna =  ricAnaClient.eseguiRicercaAnagrafica(URL, cognome, nome, annoNascita, meseNascita, giornoNascita, codiceFiscale);
////		    for(Persona p : arrPersone){
////		    	
////		    	if(p.getDatiAnagrafeRoma() != null){
////			    	System.out.println(p.getDatiAnagrafeRoma().getCodiceIndividuale());
////			    	System.out.println(p.getDatiAnagrafeRoma().getDescrizione() );
////		    	}
////		    	if(p.getPersonaCompleta() != null){
////		    		System.out.println(p.getPersonaCompleta().getNome());
////			    	System.out.println(p.getPersonaCompleta().getCognome());
////			    	System.out.println(p.getPersonaCompleta().getStatoCivile() );
////		    	}
////		    
////		    }
//		
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}
