package it.webred.ct.data.access.basic.segnalazionequalificata;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.segnalazionequalificata.dao.SegnalazioneQualificataDAO;
import it.webred.ct.data.access.basic.segnalazionequalificata.dto.PraticaSegnalazioneDTO;
import it.webred.ct.data.access.basic.segnalazionequalificata.dto.RicercaPraticaSegnalazioneDTO;
import it.webred.ct.data.model.segnalazionequalificata.SOfAmbitoAccertamento;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratAllegato;
import it.webred.ct.data.model.segnalazionequalificata.SOfPratica;
import it.webred.ct.support.audit.AuditStateless;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class SegnalazioneQualificataServiceBean extends CTServiceBaseBean 
	implements SegnalazioneQualificataService {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SegnalazioneQualificataDAO segnalazioneQualificataDAO;
	
	
	@Override
	public List<SOfAmbitoAccertamento> getAmbitiAccertamento(SegnalazioniDataIn dataIn) 
		throws SegnalazioneQualificataServiceException{
		return segnalazioneQualificataDAO.getAmbitiAccertamento(dataIn);
	}

	@Override
	@Interceptors(AuditStateless.class)
	public PraticaSegnalazioneDTO getPraticaById(SegnalazioniDataIn dataIn) throws SegnalazioneQualificataServiceException{
		return segnalazioneQualificataDAO.getPraticaById(dataIn.getRicercaPratica());
	}
	
	@Override
	@Interceptors(AuditStateless.class)
	public List<SOfPratica> search(SegnalazioniDataIn dataIn)throws SegnalazioneQualificataServiceException{
		RicercaPraticaSegnalazioneDTO rs = dataIn.getRicercaPratica();
		return segnalazioneQualificataDAO.search(rs);
	}
	
	@Override
	public List<String> getListaOperatori(SegnalazioniDataIn dataIn) 
		throws SegnalazioneQualificataServiceException{
		return segnalazioneQualificataDAO.getListaOperatori(dataIn);
	}
	
	@Override
	public Long searchCount(SegnalazioniDataIn dataIn) throws SegnalazioneQualificataServiceException{
		RicercaPraticaSegnalazioneDTO rs = dataIn.getRicercaPratica();
		return segnalazioneQualificataDAO.searchCount(rs);
	}
		
	@Override
	public List<Object> getSuggestionCognomi(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException{
		
		return segnalazioneQualificataDAO.getSuggestionCognomi(dataIn);
	}
		
	@Override
	public List<Object> getSuggestionDenominazione(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException{
		
		return segnalazioneQualificataDAO.getSuggestionDenominazione(dataIn);
	}
		
	@Override
	public List<Object> getSuggestionNomi(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException{
		
		return segnalazioneQualificataDAO.getSuggestionNomi(dataIn);
	}
		
	@Override
	public List<Object> getSuggestionCodFisc(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException{
		return segnalazioneQualificataDAO.getSuggestionCodFisc(dataIn);
	}
	
	@Override
	@Interceptors(AuditStateless.class)
	public long creaPraticaSegnalazione(PraticaSegnalazioneDTO dto)
			throws SegnalazioneQualificataServiceException {
		return segnalazioneQualificataDAO.creaPraticaSegnalazione(dto);
	}

	@Override
	@Interceptors(AuditStateless.class)
	public void modificaPraticaSegnalazione(PraticaSegnalazioneDTO dto)
			throws SegnalazioneQualificataServiceException {
		segnalazioneQualificataDAO.modificaPraticaSegnalazione(dto);
	}

	@Override
	@Interceptors(AuditStateless.class)
	public void cancellaPraticaSegnalazione(RicercaPraticaSegnalazioneDTO ro)
			throws SegnalazioneQualificataServiceException {
		segnalazioneQualificataDAO.cancellaPraticaSegnalazione(ro);
	}

	@Override
	@Interceptors(AuditStateless.class)
	public SOfPratAllegato getAllegato(RicercaPraticaSegnalazioneDTO ro)
		throws SegnalazioneQualificataServiceException{
		return segnalazioneQualificataDAO.getAllegato(ro);
	}
	
	@Override
	public List<SOfPratAllegato> getListaAllegatiPratica(SegnalazioniDataIn dataIn)
		throws SegnalazioneQualificataServiceException{
		return segnalazioneQualificataDAO.getListaAllegatiPratica(dataIn.getRicercaPratica());
	}

	@Override
	@Interceptors(AuditStateless.class)
	public void inserisciAllegato(SegnalazioniDataIn dataIn)
			throws SegnalazioneQualificataServiceException {
		SOfPratAllegato allegato = (SOfPratAllegato)dataIn.getObj1();
		segnalazioneQualificataDAO.inserisciAllegato(allegato);
	}

	@Override
	@Interceptors(AuditStateless.class)
	public void rimuoviAllegato(RicercaPraticaSegnalazioneDTO ro)
			throws SegnalazioneQualificataServiceException {
		segnalazioneQualificataDAO.rimuoviAllegato(ro);
	}
	
	@Override
	@Interceptors(AuditStateless.class)
	public void cancellaListaAllegatiPratica(SegnalazioniDataIn dataIn)
	throws SegnalazioneQualificataServiceException{
		segnalazioneQualificataDAO.cancellaListaAllegatiPratica(dataIn.getRicercaPratica());
	}


}
