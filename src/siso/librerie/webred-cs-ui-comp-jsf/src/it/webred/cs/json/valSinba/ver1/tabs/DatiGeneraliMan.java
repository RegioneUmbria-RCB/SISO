package it.webred.cs.json.valSinba.ver1.tabs;

import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiFamigliaMan.REGIONI;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class DatiGeneraliMan {
	
	public static Logger logger = Logger.getLogger("carsociale.log");

	public static final String NAME = "Dati Generali";
	
	private List<AmTabNazioni> lstNazioneResidenza;
	private List<SelectItem> lstRegioni;
	
	private List<ArTbPrestazioniInps> lstPrestazioni;
	private List<String> lstAnni;
	
	private List<String> prestazioniSel;
	
	private ComponenteAltroMan tutore;
	
	public DatiGeneraliMan()
	{
		loadListe();
	}

	public void init(Long idSoggetto, List<CsAComponente> listaComponenti)
	{
		tutore = new ComponenteAltroMan(idSoggetto, listaComponenti);
	}

	public void valorizzaComponenteMan(DatiGeneraliBean datiGeneraliBean){
		//Valorizzo dati generale
	}
	
	public void aggiungiPrestazione()
	{
		// if ()
	}
	
	public void valorizzaJson(DatiGeneraliBean datiGeneraliBean){
		//Valorizzo liste dati generali
		//datiGeneraliBean.setLstNazioneResidenza(lstNazioneResidenza);
		//datiGeneraliBean.setLstPrestazioni(lstPrestazioni);
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


	@SuppressWarnings("deprecation")
	public void loadListe(/*BaseDTO bo*/)
	{
		if (lstNazioneResidenza == null || lstNazioneResidenza.isEmpty()) {
			lstNazioneResidenza = this.getNazioni();
		}
		if (lstPrestazioni == null || lstPrestazioni.isEmpty()) {
			lstPrestazioni = new ArrayList<ArTbPrestazioniInps>();
			try {
				BaseDTO dto = new BaseDTO();
				CsUiCompBaseBean.fillEnte(dto);
				AccessTableConfigurazioneSessionBeanRemote bean = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility
						.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
								"AccessTableConfigurazioneSessionBean");
				
				
				List<ArTbPrestazioniInps> beanLstPrestazioni = bean.getPrestazioniInpsSinBa(dto);
				if (beanLstPrestazioni != null) {
					lstPrestazioni.addAll(beanLstPrestazioni);
				}
			} catch (NamingException e) {
				logger.error("getListaPrestazioni", e);
			}
		}
		if (lstAnni == null || lstAnni.isEmpty()) {
			lstAnni = new ArrayList<String>();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			for (int i=year; i>=1970; i--)
			{
				lstAnni.add(String.valueOf(i));
			}
		}
		
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

	public List<ArTbPrestazioniInps> getLstPrestazioni() {
		return lstPrestazioni;
	}

	public void setLstPrestazioni(List<ArTbPrestazioniInps> lstPrestazioni) {
		this.lstPrestazioni = lstPrestazioni;
	}

	public List<AmTabNazioni> getLstNazioneResidenza() {
		return lstNazioneResidenza;
	}

	public void setLstNazioneResidenza(List<AmTabNazioni> lstNazioneResidenza) {
		this.lstNazioneResidenza = lstNazioneResidenza;
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

	
	public List<String> getLstAnni() {
		return lstAnni;
	}

	public void setLstAnni(List<String> lstAnni) {
		this.lstAnni = lstAnni;
	}
	
}
