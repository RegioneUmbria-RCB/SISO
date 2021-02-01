package it.webred.cs.jsf.interfaces;

import it.webred.cs.csa.ejb.dto.cartella.DatiAggCasoGitDTO;
import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IDatiAna {
	
	public List<SelectItem> getLstCittadinanze();
	public ComuneNazioneNascitaMan getComuneNazioneNascitaMan();
	public Long getSoggettoId();
	public List<SelectItem> getLstCittadinanzeAcq();
	public boolean salva();
	public boolean salvaAnagraficaModificata();
	public boolean salvaAnagraficaLog();
	/*public IDatiValiditaGestione getMediciGestBean();
	public IDatiValiditaGestione getStatusGestBean();
	public IDatiValiditaGestione getStatoCivileGestBean();*/
	public void estraiAggiornamentoAnag();
	//SISO-1127
	public boolean salvaAnagraficaGit();
}
