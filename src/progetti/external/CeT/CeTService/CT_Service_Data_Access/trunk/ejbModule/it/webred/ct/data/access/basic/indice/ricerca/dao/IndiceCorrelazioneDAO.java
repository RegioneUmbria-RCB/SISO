package it.webred.ct.data.access.basic.indice.ricerca.dao;

import it.webred.ct.data.access.basic.indice.ricerca.KeyFabbricatoDTO;
import it.webred.ct.data.access.basic.indice.ricerca.KeyUIDTO;
import it.webred.ct.data.model.indice.IndicePK;

import java.util.List;

import org.apache.log4j.Logger;

public interface IndiceCorrelazioneDAO {
	public Logger logger = Logger.getLogger("ctservice.log");

	//acquisizione dati indice con input entity 
	public List<IndicePK> getViaTotalePK(String key, String enteSorgente, String progES, String destFonte, String destProgr);
	
	public List<IndicePK> getSoggettoTotalePK(String key, String enteSorgente, String progES, String destFonte, String destProgr);
	
	public List<IndicePK> getCivicoTotalePK(String key, String enteSorgente, String progES, String destFonte, String destProgr);
	
	public List<IndicePK> getFabbricatoTotalePK(String key, String enteSorgente, String progES, String destFonte, String destProgr);
	
	public List<IndicePK> getOggettoTotalePK(String key, String enteSorgente, String progES, String destFonte, String destProgr);
	
	public IndicePK getOggettoTotalePKByKey(String idDwh, String enteSorgente, String progES);
	
	//acquisizione dati indice con input chiave entità 
	public List<IndicePK> getOggettoTotalePKFromUI (KeyUIDTO keyUi, String destFonte, String destProgr);
	
	public List<IndicePK> getFabbricatoTotalePKFromUI (KeyFabbricatoDTO keyFabbr, String destFonte, String destProgr);
	

}
