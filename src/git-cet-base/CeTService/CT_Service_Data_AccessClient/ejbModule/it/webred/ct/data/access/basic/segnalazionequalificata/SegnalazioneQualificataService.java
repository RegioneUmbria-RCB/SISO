package it.webred.ct.data.access.basic.segnalazionequalificata;

import it.webred.ct.data.access.basic.segnalazionequalificata.dto.PraticaSegnalazioneDTO;
import it.webred.ct.data.access.basic.segnalazionequalificata.dto.RicercaPraticaSegnalazioneDTO;
import it.webred.ct.data.model.segnalazionequalificata.SOfAmbitoAccertamento;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratAllegato;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratica;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface SegnalazioneQualificataService {

	public List<SOfAmbitoAccertamento> getAmbitiAccertamento(SegnalazioniDataIn dataIn) 
		throws SegnalazioneQualificataServiceException;
	
	public List<SOfPratica> search(SegnalazioniDataIn dataIn) 
		throws SegnalazioneQualificataServiceException;
	
	public Long searchCount(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;
		
	public List<Object> getSuggestionCognomi(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;
		
	public List<Object> getSuggestionDenominazione(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;
	
	public List<Object> getSuggestionNomi(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;
		
	public List<Object> getSuggestionCodFisc(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;
	
	public PraticaSegnalazioneDTO getPraticaById(SegnalazioniDataIn dataIn) 
		throws SegnalazioneQualificataServiceException;
	
	public List<String> getListaOperatori(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;
	
	public long creaPraticaSegnalazione(PraticaSegnalazioneDTO dto)
		throws SegnalazioneQualificataServiceException;
	
	public void modificaPraticaSegnalazione(PraticaSegnalazioneDTO dto)
		throws SegnalazioneQualificataServiceException;

	public void cancellaPraticaSegnalazione(RicercaPraticaSegnalazioneDTO ro)
		throws SegnalazioneQualificataServiceException;

	public void inserisciAllegato(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException ;

	public void rimuoviAllegato(RicercaPraticaSegnalazioneDTO ro)
		throws SegnalazioneQualificataServiceException;
	
	public SOfPratAllegato getAllegato(RicercaPraticaSegnalazioneDTO ro)
		throws SegnalazioneQualificataServiceException;

	public List<SOfPratAllegato> getListaAllegatiPratica(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;
	
	public void cancellaListaAllegatiPratica(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException;


}
