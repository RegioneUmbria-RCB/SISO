package it.webred.cs.csa.ejb.dto.configurazione;

import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsOSettoreBASIC;

import java.io.Serializable;
import java.util.List;

public class SettoreCatSocialeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private CsOSettoreBASIC settore;
	private List<CsCCategoriaSociale> lstCatSociale;
	
	public CsOSettoreBASIC getSettore() {
		return settore;
	}
	public void setSettore(CsOSettoreBASIC settore) {
		this.settore = settore;
	}
	public List<CsCCategoriaSociale> getLstCatSociale() {
		return lstCatSociale;
	}
	public void setLstCatSociale(List<CsCCategoriaSociale> lstCatSociale) {
		this.lstCatSociale = lstCatSociale;
	}
}
