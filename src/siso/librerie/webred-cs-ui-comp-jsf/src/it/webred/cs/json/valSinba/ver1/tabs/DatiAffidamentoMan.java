package it.webred.cs.json.valSinba.ver1.tabs;

import java.util.List;

import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;

import org.jboss.logging.Logger;

public class DatiAffidamentoMan {
	
	public static Logger logger = Logger.getLogger("carsociale.log");

	public static final String NAME = "Affidamento";
	
	private ComponenteAltroMan tutore;
	
	public DatiAffidamentoMan()
	{
		
		
	}
	
	public void init(Long idSoggetto, List<CsAComponente> listaParenti)
	{
		tutore = new ComponenteAltroMan(idSoggetto, listaParenti);
	}

	public void valorizzaComponenteMan(DatiAffidamentoBean datiAffidamentoBean){
		//Valorizzo dati generale
		
	}
	
	public void aggiungiComponente()
	{
		
	}
	
	public void valorizzaJson(DatiAffidamentoBean datiAffidamentoBean){
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
