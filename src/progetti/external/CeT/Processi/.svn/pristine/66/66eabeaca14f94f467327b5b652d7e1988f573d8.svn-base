package it.webred.ct.proc.ario.normalizzazione;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.data.model.indice.SitFabbricatoTotale;
import it.webred.ct.data.model.indice.SitOggettoTotale;
import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
import it.webred.ct.proc.ario.bean.Civico;
import it.webred.ct.proc.ario.bean.Indirizzo;
import it.webred.ct.proc.ario.fonti.civico.catasto.CivicoCatastoCatastale;
//import it.webred.ct.proc.ario.bean.SitCivicoTotale;

//import it.webred.ct.proc.ario.bean.SitOggettoTotale;
//import it.webred.ct.proc.ario.bean.SitSoggettoTotale;
//import it.webred.ct.proc.ario.bean.SitViaTotale;
import it.webred.ct.proc.ario.utils.NormalizzaUtil;


/**
 * @author Bravi Alessandro
 *
 */

public class NormalizzaTotali {

	protected static final Logger log = Logger.getLogger(NormalizzaTotali.class.getName());

	/*
	 * Metodo per la normalizzazione dei dati del soggetto 
	 */	
	public void normalizzaSoggetto(SitSoggettoTotale sst) throws Exception{
						
		try{
		
			//Trasformazione minuscolo->maiuscolo
			NormalizzaUtil.getUpperSoggetti(sst); 
			
			//Separazione titoli
			sst.setNome(NormalizzaUtil.separaTitoli(sst.getNome()));
			sst.setCognome(NormalizzaUtil.separaTitoli(sst.getCognome()));
			sst.setDenominazione(NormalizzaUtil.separaTitoli(sst.getDenominazione()));
								
		}catch(Exception e){
			sst.setAnomalia("Errore in normalizzazione dati Soggetto: " + e);
			log.error("Errore in normalizzazione dati Soggetto" , e);
		}
				
	}
	
	
	/*
	 * Metodo per la normalizzazione dei dati dell'oggetto
	 */
	public void normalizzaOggetto(SitOggettoTotale sot) throws Exception{
					
		try{
		
			//Trasformazione minuscolo->maiuscolo
			NormalizzaUtil.getUpperOggetti(sot); 	
			
			//Effettuo il padding dei campi
			NormalizzaUtil.paddingOggetto(sot);
	
		}catch(Exception e){
			sot.setAnomalia("Errore in normalizzazione dati Oggetto: " + e);
			log.error("Errore in normalizzazione dati Oggetto" , e);

		}
		
	}
	
	
	/*
	 * Metodo per la normalizzazione dei dati della via
	 */
	public void normalizzaVia(SitViaTotale svt) throws Exception{
		
		try{
		
			//Trasformazione minuscolo->maiuscolo
			NormalizzaUtil.getUpperVie(svt); 
			
			//Separo i prefissi toponomastici
			/*Indirizzo hashIndirizzo = GestioneStringheVie.restituisciIndirizzo(svt.getIndirizzo(),svt.getSedime());			
			svt.setIndirizzo(hashIndirizzo.getInd().toString());
			svt.setSedime(hashIndirizzo.getSed().toString());*/
			
			/*Collection collCivici = GestioneStringheVie.restituisciCivico(svt.getIndirizzo());
			Iterator iter = collCivici.iterator();
			if(iter.hasNext()) {
				Civico civ = (Civico)iter.next();
				svt.setIndirizzo(civ.getInd().toString());
				svt.setSedime(civ.getSed().toString());
				svt.setNote(civ.getNote().toString());
			}*/
												
		}catch(Exception e){		
			svt.setAnomalia("Errore in normalizzazione dati Via: " + e);
			log.error("Errore in normalizzazione dati Via" , e);

		}

	}
	
	
	/*
	 * Metodo per la normalizzazione dei dati del civico
	 */
	public void normalizzaCivico(SitCivicoTotale sct) throws Exception{
						
		try{
													
			//Trasformazione minuscolo->maiuscolo			 
			sct.setNote(NormalizzaUtil.normalizzaAccentiAndUpper(sct.getNote()));
			
		}catch(Exception e){
			sct.setAnomalia("Errore in normalizzazione dati Civico: " + e);
			log.error("Errore in normalizzazione dati Civico" , e);
		}
						
		
	}
	
	
	
	/*
	 * Metodo per la normalizzazione dei dati del fabbricato
	 */
	public static void normalizzaFabbricato(SitFabbricatoTotale sft) throws Exception{
					
		try{
		
			//Trasformazione minuscolo->maiuscolo
			NormalizzaUtil.getUpperFabbricati(sft); 	
			
			//Effettuo il padding dei campi
			NormalizzaUtil.paddingFabbricato(sft);
	
		}catch(Exception e){
			sft.setAnomalia("Errore in normalizzazione dati Fabbricato: " + e);
			log.error("Errore in normalizzazione dati Fabbricato" , e);
		}
		
	}
	
}
