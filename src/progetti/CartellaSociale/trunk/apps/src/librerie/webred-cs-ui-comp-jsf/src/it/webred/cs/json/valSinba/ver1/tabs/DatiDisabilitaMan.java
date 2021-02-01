package it.webred.cs.json.valSinba.ver1.tabs;

import java.util.List;

import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;

import org.jboss.logging.Logger;

public class DatiDisabilitaMan {
	
	public static Logger logger = Logger.getLogger("carsociale.log");

	public static final String NAME = "Dati Disabilità";
	
	private ComponenteAltroMan tutore;
	
	public DatiDisabilitaMan()
	{
		
		
	}
	
	public void init(Long idSoggetto, List<CsAComponente> listaComponenti)
	{
		tutore = new ComponenteAltroMan(idSoggetto);
	}

	public void valorizzaComponenteMan(DatiDisabilitaBean datiDisabilitaBean){
		//Valorizzo dati generale
		
	}
	
	public void aggiungiComponente()
	{
		
	}
	
	public void valorizzaJson(DatiDisabilitaBean datiDisabilitaBean){
		//Valorizzo dati componente familiare
		//datiFamigliaBean.setLstCittadinanze(lstCittadinanza);
		//datiFamigliaBean.setLstTitoliStudio(lstTitoliStudio);
		//datiFamigliaBean.setLstTitoliStudio(lstNazioni);
	}
	

	public void loadListe()
	{
		
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

	
}
