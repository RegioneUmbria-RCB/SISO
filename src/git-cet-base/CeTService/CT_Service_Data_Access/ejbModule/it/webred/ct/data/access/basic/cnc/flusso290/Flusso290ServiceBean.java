package it.webred.ct.data.access.basic.cnc.flusso290;
 
import it.webred.ct.data.access.basic.cnc.CNCBaseService;
import it.webred.ct.data.access.basic.cnc.flusso290.Flusso290Service;
import it.webred.ct.data.access.basic.cnc.flusso290.Flusso290ServiceException;
import it.webred.ct.data.access.basic.cnc.flusso290.dao.Flusso290DAO;
import it.webred.ct.data.access.basic.cnc.flusso290.dao.QueryBuilder;
import it.webred.ct.data.access.basic.cnc.flusso290.dto.AnagraficaImpostaDTO;
import it.webred.ct.data.access.basic.cnc.flusso290.dto.Flusso290SearchCriteria;
import it.webred.ct.data.model.cnc.flusso290.RAnagraficaIntestatarioRuolo;
import it.webred.ct.data.model.cnc.flusso290.RDatiContabili;
import it.webred.ct.support.audit.AuditStateless;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class Flusso290ServiceBean
 */
@Stateless
public class Flusso290ServiceBean extends CNCBaseService implements Flusso290Service {

	@Autowired
	private Flusso290DAO flusso290DAO;
	
	@Override
	@Interceptors(AuditStateless.class)
	public List<AnagraficaImpostaDTO> getAnagraficaImpostaSintesi(
			Flusso290SearchCriteria criteria, int startm, int numberRecord) {
		
		List<AnagraficaImpostaDTO> result = new ArrayList<AnagraficaImpostaDTO>();

		try {
			
			List<RAnagraficaIntestatarioRuolo> anagList = flusso290DAO.getIntestarioRuolo(criteria, startm, numberRecord);
			
			
			for (RAnagraficaIntestatarioRuolo anag : anagList) {
				
				
				AnagraficaImpostaDTO anagImp = new AnagraficaImpostaDTO();
				
				anagImp.setAnagraficaIntestatario(anag);
				
				List<RDatiContabili> datiContList = flusso290DAO.getDatiContByAnag(anag);
				anagImp.setDatiContabili(datiContList);
				
				result.add(anagImp);
			}
		}
		catch(Throwable t) {
			throw new Flusso290ServiceException(t);
		}
		
		return result;

	}

	@Override
	public Long getRecordCount(Flusso290SearchCriteria criteria) {
		try {
			return flusso290DAO.getRecordCount(criteria);
		}
		catch(Throwable t) {
			throw new Flusso290ServiceException(t);
		}
	}





}
