package it.webred.cs.json.familiariConviventi;

import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.json.ISchedaValutazione;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IFamConviventi extends ISchedaValutazione {

	public List<SelectItem> getListaTipologiaNucleo();

	public List<SelectItem> getListaGruppoVulnerabile();
	
	public List<CsTbTipologiaFamiliare> getLstCsTbTipologiaFam();
	
	public void changeTipoNucleo();

	public void changeGruppoVulnerabile();
	
	public long getTipologiaFamiliareId();

	public void changeConFigli();
	
	
}
