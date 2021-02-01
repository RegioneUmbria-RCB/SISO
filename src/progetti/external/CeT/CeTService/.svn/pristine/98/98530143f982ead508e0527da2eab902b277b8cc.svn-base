package it.webred.ct.data.access.basic.segnalazionequalificata.dao;

import it.webred.ct.data.access.basic.segnalazionequalificata.SegnalazioneQualificataServiceException;
import it.webred.ct.data.access.basic.segnalazionequalificata.SegnalazioniDataIn;
import it.webred.ct.data.access.basic.segnalazionequalificata.dto.PraticaSegnalazioneDTO;
import it.webred.ct.data.access.basic.segnalazionequalificata.dto.RicercaPraticaSegnalazioneDTO;
import it.webred.ct.data.model.segnalazionequalificata.SOfAmbitoAccertamento;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratAllegato;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratica;

import java.util.List;

public interface SegnalazioneQualificataDAO {
	
	public List<SOfAmbitoAccertamento> getAmbitiAccertamento(SegnalazioniDataIn dataIn) 
		throws SegnalazioneQualificataServiceException;

	public PraticaSegnalazioneDTO getPraticaById(RicercaPraticaSegnalazioneDTO ro) 
		throws SegnalazioneQualificataServiceException;

	public List<SOfPratica> search(RicercaPraticaSegnalazioneDTO rs)
		throws SegnalazioneQualificataServiceException;
	
	public Long searchCount(RicercaPraticaSegnalazioneDTO rs)
		throws SegnalazioneQualificataServiceException;
	
	public List<Object> getSuggestionCognomi(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;
	
	public List<Object> getSuggestionDenominazione(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;

	public List<Object> getSuggestionNomi(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;
	
	public List<Object> getSuggestionCodFisc(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;
	
	public List<String> getListaOperatori(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;
	
	public long creaPraticaSegnalazione(PraticaSegnalazioneDTO dto)
		throws SegnalazioneQualificataServiceException;

	public void modificaPraticaSegnalazione(PraticaSegnalazioneDTO dto)
		throws SegnalazioneQualificataServiceException;
	
	public void cancellaPraticaSegnalazione(RicercaPraticaSegnalazioneDTO ro)
		throws SegnalazioneQualificataServiceException;
	
	public SOfPratAllegato getAllegato(RicercaPraticaSegnalazioneDTO ro)
		throws SegnalazioneQualificataServiceException;
	
	public List<SOfPratAllegato> getListaAllegatiPratica(RicercaPraticaSegnalazioneDTO ro)
		throws SegnalazioneQualificataServiceException;
	
	public void cancellaListaAllegatiPratica(RicercaPraticaSegnalazioneDTO ro)
		throws SegnalazioneQualificataServiceException;
	
	public void inserisciAllegato(SOfPratAllegato allegato)
		throws SegnalazioneQualificataServiceException;
	
	public void rimuoviAllegato(RicercaPraticaSegnalazioneDTO ro)
		throws SegnalazioneQualificataServiceException;
	
	
}
