package it.webred.cs.json.provvedimentiMinori.tabs;

import java.util.List;

import it.webred.cs.jsf.manbean.ComponenteAltroMan;
import it.webred.cs.json.provvedimentiMinori.ver1.tabs.DatiProvvedimentoBean;

public interface IDatiProvvedimento {

	public String getTabName();	
	
	public List<String> getLstDatiProvvedimento();	
	
	public List<String> getLstTipoProvvedimento();
	
	public List<String> getLstNaturaProvvedimento();
	
	public List<String> getLstTipoAffidamento();
	
	public List<String> getLstIndagine();
	
	public List<String> getLstAffidamento();
	
	public List<String> getLstCessazionePotesta();
	
	public ComponenteAltroMan getTutore();
	
	public void setTutore(ComponenteAltroMan tutore);

	public void init(Long idSoggetto);

	public void valorizzaComponenteMan(DatiProvvedimentoBean datiProvBean);

	public void valorizzaJson(DatiProvvedimentoBean datiProvBean);

}
