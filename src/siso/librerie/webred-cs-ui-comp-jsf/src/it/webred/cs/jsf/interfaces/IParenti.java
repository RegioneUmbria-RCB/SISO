package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsAComponente;

import java.util.Date;
import java.util.List;

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
	public Boolean isHaParenti();
	public Boolean isParentiSconosciuti();
	public Boolean isViveSolo();
	public String getHaParenti();
	public String getParentiSconosciuti();
	public String getViveSolo();
	public boolean loadStatoRdC(CsAComponente c);
	public void onSelectComponenteDaAltraScheda();
	
}
