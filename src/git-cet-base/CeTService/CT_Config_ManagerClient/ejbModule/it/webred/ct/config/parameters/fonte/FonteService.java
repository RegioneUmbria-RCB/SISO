package it.webred.ct.config.parameters.fonte;

import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmFonteTipoinfo;
import it.webred.ct.config.parameters.ParameterService;


import java.util.List;

import javax.ejb.Remote;

@Remote
public interface FonteService extends ParameterService{
	
	public List<AmFonte> getListaFonte();
	
	public List<AmFonte> getListaFonteAll();

	public List<AmFonteTipoinfo> getListaFonteTipoinfoByFonte(String fonte);
	
	public AmFonte getFonte(Long idFonte);
	
	/**
	 * Ritorna la lista delle fontin associate all'ente distinta per tipo fonte
	 * @param belfioreComune
	 * codice belfiore del comune  
	 * @param tipoFonte 
	 * se uguale (E) fonti esterne 
	 * se uguale (I) fonti interne
	 * se almeno uno dei due parametri non viene valorizzato ritorna tutte le fonti
	 * @return
	 */
	public List<AmFonte> getListaFonteByComuneETipoFonte(String belfioreComune, String tipoFonte);
	
}
