package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.jsf.manbean.ComuneResidenzaMan;
import it.webred.cs.jsf.manbean.IndirizzoMan;

import java.util.List;




public interface IGestioneSettori {
	
	public void caricaSettori();
	public void attivaOrganizzazioni();
	public void disattivaOrganizzazioni();
	public void eliminaOrganizzazioni();
	public void nuovaOrganizzazione();
	public void aggiungiSettore();
	public void attivaSettori();
	public void disattivaSettori();
	public void eliminaSettori();
	
	public List<CsOOrganizzazione> getLstOrganizzazioni();
	public List<CsOOrganizzazione> getSelectedOrganizzazioni();
	public CsOOrganizzazione getSelectedOrganizzazione();
	public CsOOrganizzazione getNewOrganizzazione();
	public boolean isRenderSettori();
	public List<CsOSettoreBASIC> getLstSettori();
	public List<CsOSettoreBASIC> getSelectedSettori();
	public CsOSettore getNewSettore();
	public IndirizzoMan getNewIndirizzo();
	public ComuneResidenzaMan getNewComune();

}
