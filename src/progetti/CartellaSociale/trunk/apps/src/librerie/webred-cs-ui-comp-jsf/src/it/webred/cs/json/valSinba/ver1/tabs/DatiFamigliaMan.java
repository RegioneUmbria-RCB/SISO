package it.webred.cs.json.valSinba.ver1.tabs;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
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
	private List<SelectItem> lstTitoliStudio;
	private List<AmTabNazioni> lstNazioni;
	
	private List<SelectItem> lstRegioni;
	private List<SelectItem> lstOccupazioni;
 	
	@JsonIgnore private ComponenteAltroMan tutore;
	
	@JsonIgnore private BaseDTO baseDtoParam=new BaseDTO();
	
	public DatiFamigliaMan()
	{
		loadListe();
	}
	
	public void init(Long idSoggetto, List<CsAComponente> listaComponenti)
	{
		tutore = new ComponenteAltroMan(idSoggetto);
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
	
	public List<SelectItem> getTitoli() {
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
		return lstTitoliStudio;
	}

	public void setLstTitoliStudio(List<SelectItem> lstTitoliStudio) {
		this.lstTitoliStudio = lstTitoliStudio;
	}

	public List<AmTabNazioni> getLstNazioni() {
		return lstNazioni;
	}

	public void setLstNazioni(List<AmTabNazioni> lstNazioni) {
		this.lstNazioni = lstNazioni;
	}

	public List<SelectItem> getLstRegioni() {
		return lstRegioni;
	}

	public void setLstRegioni(List<SelectItem> lstRegioni) {
		this.lstRegioni = lstRegioni;
	}

	public List<SelectItem> getLstOccupazioni() {
		return lstOccupazioni;
	}

	public void setLstOccupazioni(List<SelectItem> lstOccupazioni) {
		this.lstOccupazioni = lstOccupazioni;
	}
	
}
