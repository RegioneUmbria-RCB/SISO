package it.webred.ct.data.access.basic.cnc.flusso290.dao;

import it.webred.ct.data.access.basic.cnc.dao.CNCDAOException;
import it.webred.ct.data.access.basic.cnc.flusso290.dto.Flusso290SearchCriteria;
import it.webred.ct.data.model.cnc.flusso290.RAnagraficaIntestatarioRuolo;
import it.webred.ct.data.model.cnc.flusso290.RDatiContabili;

import java.util.List;

public interface Flusso290DAO {

	
	public Long getRecordCount(Flusso290SearchCriteria criteria) throws CNCDAOException;;

	public List<RAnagraficaIntestatarioRuolo> getIntestarioRuolo(Flusso290SearchCriteria criteria, int startm, int numberRecord) throws CNCDAOException ;
	
	public List<RDatiContabili> getDatiContByAnag(RAnagraficaIntestatarioRuolo anag) throws CNCDAOException ;


}
