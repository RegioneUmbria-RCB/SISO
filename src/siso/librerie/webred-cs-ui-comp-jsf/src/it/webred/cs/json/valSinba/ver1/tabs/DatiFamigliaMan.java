package it.webred.cs.json.valSinba.ver1.tabs;

import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DatiFamigliaMan {
	
	public static Logger logger = Logger.getLogger("carsociale.log");

	public static final String NAME = "Dati Famiglia";
	
	private List<AmTabNazioni> lstCittadinanza;
	private List<AmTabNazioni> lstNazioni;
	
	private List<SelectItem> lstRegioni;
	private List<SelectItem> lstOccupazioni;
	private List<SelectItem> lstTitoliStudio;
	private List<SelectItem> lstTipiComponente;
 	
	@JsonIgnore private ComponenteAltroMan tutore;
	
	@JsonIgnore private BaseDTO baseDtoParam=new BaseDTO();
	
	public static enum TIPO_COMPONENTE{
		MADRE("Madre", 1),
		PADRE("Padre", 2),
		FRATELLI_SORELLE("Fratelli/Sorelle", 3),
		NONNI("Nonno/a", 4),
		COMPAGNO_GENITORE("Compagno/a della madre o del padre",5),
		ALTRI("Altri conviventi",6);
		
		String descrizione;
        int codice;
		
        private TIPO_COMPONENTE(String descrizione, int codice) {
			this.descrizione = descrizione;
			this.codice = codice;
		}

		public String getDescrizione() {return descrizione;}
		public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
		public int getCodice() {return codice;}
		public void setCodice(int codice) {this.codice = codice;}
		
		public static String getDescrizioneByCodice(int id) {
		    for(TIPO_COMPONENTE e : values()) {
		        if(e.codice == id) return e.getDescrizione();
		    }
		    return "";
		}
	}
	
	public static enum REGIONI{
		ABRUZZO("Abruzzo", 1),
		BASILICATA("Basilicata" ,2),
		BOLZANO("Bolzano" ,3),
		CALABRIA("Calabria" ,4),
		CAMPANIA("Campania" ,5),
		EMILIA("Emilia Romagna" ,6),
		FRIULI("Friuli Venezia Giulia" ,7),
		LAZIO("Lazio" ,8),
		LIGURIA("Liguria" ,9),
		LOMBARDIA("Lombardia" ,10),
		MARCHE("Marche" ,11),
		MOLISE("Molise" ,12),
		PIEMONTE("Piemonte" ,13),
		PUGLIA("Puglia" ,14),
		SARDEGNA("Sardegna" ,15),
		SICILIA("Sicilia" ,16),
		TOSCANA("Toscana" ,17),
		TRENTO("Trento" ,18),
		UMBRIA("Umbria" ,19),
		VALLE_AOSTA("Valle D'Aosta" ,20),
		VENETO("Veneto" ,21);
		
		String descrizione;
        int codice;
		
        private REGIONI(String descrizione, int codice) {
			this.descrizione = descrizione;
			this.codice = codice;
		}

		public String getDescrizione() {return descrizione;}
		public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
		public int getCodice() {return codice;}
		public void setCodice(int codice) {this.codice = codice;}

		public static String getDescrizioneByCodice(int id) {
		    for(REGIONI e : values()) {
		        if(e.codice == id) return e.getDescrizione();
		    }
		    return "";
		}
	}
	
	public static enum TITOLO_STUDIO{
		NESSUN_TITOLO("Nessun Titolo", 1),
		LIC_ELEMENTARE("Licenza Elementare", 2),
		LIC_MEDIA("Licenza Media", 3),
		QUALIFICA_PROFESSIONALE("Qualifica Professionale", 4),
		DIPLOMA("Diploma Scuola Superiore", 5),
		LAUREA("Laurea o Diploma di Laurea", 6);
		
		String descrizione;
        int codice;
		
        private TITOLO_STUDIO(String descrizione, int codice) {
			this.descrizione = descrizione;
			this.codice = codice;
		}

		public String getDescrizione() {return descrizione;}
		public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
		public int getCodice() {return codice;}
		public void setCodice(int codice) {this.codice = codice;}

		public static String getDescrizioneByCodice(int id) {
		    for(TITOLO_STUDIO e : values()) {
		        if(e.codice == id) return e.getDescrizione();
		    }
		    return "";
		}
	}
	
	public static enum OCCUPAZIONE{
		OCCUPATO("Occupato", 1),
		DISOCCUPATO("Disoccupato alla ricerca di nuova occupazione", 2),
		PRIMA_OCCUPAZIONE("In cerca di prima occupazione", 3),
		CASALINGA("Casalinga/o", 4),
		STUDENTE("Studente", 5),
		RITIRATO("Ritirato/a dal lavoro", 6),
		INABILE("Inabile al lavoro", 7),
		ALTRO("In altra condizione",8);
		
		String descrizione;
        int codice;
		
        private OCCUPAZIONE(String descrizione, int codice) {
			this.descrizione = descrizione;
			this.codice = codice;
		}

		public String getDescrizione() {return descrizione;}
		public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
		public int getCodice() {return codice;}
		public void setCodice(int codice) {this.codice = codice;}

		public static String getDescrizioneByCodice(int id) {
		    for(OCCUPAZIONE e : values()) {
		        if(e.codice == id) return e.getDescrizione();
		    }
		    return "";
		}
	}
	
	
	public DatiFamigliaMan()
	{
		loadListe();
	}
	
	public void init(Long idSoggetto, List<CsAComponente> listaComponenti)
	{
		tutore = new ComponenteAltroMan(idSoggetto, listaComponenti);
	}

	public void valorizzaComponenteMan(DatiFamigliaBean datiFamigliaBean){
		//Valorizzo dati generale
		
	}
	
	public void aggiungiComponente()
	{
		
	}
	
	public void valorizzaJson(DatiFamigliaBean datiFamigliaBean){
		//Valorizzo dati componente familiare
		//datiFamigliaBean.setLstCittadinanze(lstCittadinanza);
		//datiFamigliaBean.setLstTitoliStudio(lstTitoliStudio);
		//datiFamigliaBean.setLstTitoliStudio(lstNazioni);
	}
		
	public List<AmTabNazioni> getNazioni()
	{
		List<AmTabNazioni> lstNazioneResidenza = new ArrayList<AmTabNazioni>();
		try {
			AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
								"AccessTableNazioniSessionBean");
			List<AmTabNazioni> beanLstNazioni = bean.getNazioni();
			
            if(beanLstNazioni != null && beanLstNazioni.size()>0){
			Iterator<AmTabNazioni> it=beanLstNazioni.iterator();
			while(it.hasNext()){
				AmTabNazioni nazione=it.next();
				if(nazione.getIso3166()==null){
					it.remove();
				}
			}
            }
			if (beanLstNazioni != null) {
				lstNazioneResidenza.addAll(beanLstNazioni);
			}
		} catch (NamingException e) {
			logger.error("getListaNazioni", e);
		}
		
		return lstNazioneResidenza;
	}
	
/*	public List<SelectItem> getTitoli() {
		List<SelectItem> titoliStudio = new ArrayList<SelectItem>();
		
		try {
			AccessTableConfigurazioneSessionBeanRemote bean = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB","AccessTableConfigurazioneSessionBean");
			CeTBaseObject bo = new CeTBaseObject();
			List<KeyValueDTO> lst = bean.getTitoliStudio(bo);
			if (lst != null) {
				for (KeyValueDTO kv : lst) {
					SelectItem si = new SelectItem(kv.getCodice(), kv.getDescrizione());
					si.setDisabled(!kv.isAbilitato());
					titoliStudio.add(si);
				}
			}		
		} catch (NamingException e) {
			logger.error("getTitoli", e);
		}
		
		return titoliStudio;
	}
	*/
	public List<AmTabNazioni> getCittadinanze() {
		List<AmTabNazioni> cittadinanze = new ArrayList<AmTabNazioni>();

		try {
			AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
							"AccessTableNazioniSessionBean");
			List<AmTabNazioni> beanLstNazioni = bean.getNazioni();
			if (beanLstNazioni != null) {
				cittadinanze.addAll(beanLstNazioni);
			}
			
			if (cittadinanze.size() > 0) {
				Iterator<AmTabNazioni> iterator=cittadinanze.iterator();
				while(iterator.hasNext()){
					AmTabNazioni cittadinanza = iterator.next();
					if(cittadinanza.getIso3166()==null){
						iterator.remove();
					}
				}
				Collections.sort(cittadinanze, new Comparator<AmTabNazioni>() {
					@Override
					public int compare(final AmTabNazioni object1, final AmTabNazioni object2) {
						return object1.getNazionalita().compareTo(object2.getNazionalita());
					}
				});
			}
			
			/*
			List<String> beanLstCittadinanze = bean.getCittadinanze();
			// List<AmTabNazioni> beanLstNazioni = bean.getNazioni();
			if (beanLstCittadinanze != null) {
				for (String cittadinanza : beanLstCittadinanze) {
					// in AM_TAB_NAZIONI il campo NAZIONALITA ha lunghezza
					// 500, in CS_A_SOGGETTO il campo CITTADINANZA ha
					// lunghezza 255
					if (cittadinanza.length() > 255) {
						cittadinanza = cittadinanza.substring(0, 252) + "...";
					}
					cittadinanze
							.add(new SelectItem(cittadinanza, cittadinanza));
				}
			}
			*/
		} catch (NamingException e) {
			logger.error("getCittadinanze", e);
		}

		return cittadinanze;
	}
	
	public List<SelectItem> getLstTipiComponente(){
		List<TIPO_COMPONENTE> lst =  Arrays.asList(TIPO_COMPONENTE.values());
		lstTipiComponente = new ArrayList<SelectItem>();
		for(TIPO_COMPONENTE tipo : lst ){
			SelectItem si = new SelectItem(tipo.getCodice(), tipo.getDescrizione());
			lstTipiComponente.add(si);
		}
		return lstTipiComponente;
	}
	
	public void loadListe()
	{
		lstCittadinanza = this.getCittadinanze();
		//lstTitoliStudio = this.getTitoli();
		lstNazioni = this.getNazioni();
	}

	

	// **GETTER AND SETTER////

	public String getTabName()
	{
		return NAME;
	}

	public ComponenteAltroMan getTutore() {
		return tutore;
	}

	public void setTutore(ComponenteAltroMan tutore) {
		this.tutore = tutore;
	}

	public List<AmTabNazioni> getLstCittadinanza() {
		return lstCittadinanza;
	}

	public void setLstCittadinanza(List<AmTabNazioni> lstCittadinanza) {
		this.lstCittadinanza = lstCittadinanza;
	}

	public List<SelectItem> getLstTitoliStudio() {
		List<TITOLO_STUDIO> lst =  Arrays.asList(TITOLO_STUDIO.values());
		lstTitoliStudio = new ArrayList<SelectItem>();
		for(TITOLO_STUDIO tipo : lst ){
			SelectItem si = new SelectItem(tipo.getCodice(), tipo.getDescrizione());
			lstTitoliStudio.add(si);
		}
		return lstTitoliStudio;
	}

	public List<AmTabNazioni> getLstNazioni() {
		return lstNazioni;
	}

	public void setLstNazioni(List<AmTabNazioni> lstNazioni) {
		this.lstNazioni = lstNazioni;
	}

	public List<SelectItem> getLstRegioni() {
		List<REGIONI> lst =  Arrays.asList(REGIONI.values());
		lstRegioni = new ArrayList<SelectItem>();
		for(REGIONI tipo : lst ){
			SelectItem si = new SelectItem(tipo.getCodice(), tipo.getDescrizione());
			lstRegioni.add(si);
		}
		return lstRegioni;
	}

	public List<SelectItem> getLstOccupazioni() {
		List<OCCUPAZIONE> lst =  Arrays.asList(OCCUPAZIONE.values());
		lstOccupazioni = new ArrayList<SelectItem>();
		for(OCCUPAZIONE tipo : lst ){
			SelectItem si = new SelectItem(tipo.getCodice(), tipo.getDescrizione());
			lstOccupazioni.add(si);
		}
		return lstOccupazioni;
	}

}
