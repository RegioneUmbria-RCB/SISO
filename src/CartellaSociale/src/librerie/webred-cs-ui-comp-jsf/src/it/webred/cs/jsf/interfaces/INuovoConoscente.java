package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.manbean.superc.ComuneNazioneMan;

import java.util.List;

import javax.faces.model.SelectItem;

public interface INuovoConoscente {
	
	public void reset();
	public Long getIdParentela();
	public String getCognome();
	public String getNome();
	public String getIndirizzo();
	public String getCivico();
	public String getTelefono();
	public String getCellulare();
	public String getNote();
	public ComuneNazioneMan getComuneNazioneResidenzaBean();
	public List<SelectItem> getLstParentela();
	public CsTbTipoRapportoCon getRapportoModel();
	
}
