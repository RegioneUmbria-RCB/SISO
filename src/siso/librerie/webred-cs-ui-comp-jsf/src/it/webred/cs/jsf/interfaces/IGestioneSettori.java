package it.webred.cs.jsf.interfaces;

import it.webred.cs.csa.ejb.dto.configurazione.SettoreCatSocialeDTO;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.manbean.ComuneResidenzaMan;
import it.webred.cs.jsf.manbean.IndirizzoMan;

import java.util.List;

import javax.faces.model.SelectItem;

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
	public List<SettoreCatSocialeDTO> getLstSettori();
	public List<SettoreCatSocialeDTO> getSelectedSettori();
	public CsOSettore getNewSettore();
	public IndirizzoMan getNewIndirizzo();
	public ComuneResidenzaMan getNewComune();
	public List<SelectItem> getLstCatSociali();
	public List<Long> getLstCatSocialiSel();
	public void setLstCatSocialiSel(List<Long> lstCatSocialiSel);
	public void salva2Liv();

}
