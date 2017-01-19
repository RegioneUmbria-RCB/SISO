package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsAComponente;

import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;

public interface IParenti {
	
	public void salvaNuovoParente();
	public void salvaNuovoConoscente();
	public void loadModificaParente();
	public void loadModificaConoscente();
	public void eliminaParente();
	public void eliminaConoscente();
	public List<CsAComponente> getLstParenti();
	public int getIdxSelected();
	public List<CsAComponente> getLstConoscenti();
	public boolean isNuovo();
	public String calcoloEta(Date dataNascita, Date dataDecesso);
	
}
