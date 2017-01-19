package it.webred.cs.jsf.interfaces;

import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IDatiAna {
	
	public List<SelectItem> getLstCittadinanze();
	public ComuneNazioneNascitaMan getComuneNazioneNascitaMan();
	public Long getSoggettoId();
	public List<SelectItem> getLstCittadinanzeAcq();
	
	/*public IDatiValiditaGestione getMediciGestBean();
	public IDatiValiditaGestione getStatusGestBean();
	public IDatiValiditaGestione getStatoCivileGestBean();*/

}
