package it.webred.cs.json.stranieri;

import it.webred.cs.json.ISchedaValutazione;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IStranieri extends ISchedaValutazione {

	public List<String> getListaAnni();

	public List<SelectItem> getListaPermessi();

	public List<SelectItem> getListaNazioni();

	public List<SelectItem> getListaStatus();

	public void changeStatoPermesso();

	public void changeStatus();
	
	public void changeNazioneOrigine();
	
	public void changeNazioneProvenienza();
	
	public void changePermesso();
	
	public void  changeSenzaPermessoSoggiorno();  //SISO-792

	public void changeProtezioneInternazionale();

	public void changeLinguaItaAttestato();

	public void changeEtaNonScolastica();
	
	public void changeMinoreNonAccompagnato();

	public void changePresente();
	
	public void setValidaConoscenzaLingua(boolean valida);

	public void setValidaCampiImmigrazione(boolean valida);

	public void setValidaProfugoMigrante(boolean valida);

	public List<SelectItem> getLstArrivoItalia();

	public boolean isArrivoInItaliaMigrante();
	
}
