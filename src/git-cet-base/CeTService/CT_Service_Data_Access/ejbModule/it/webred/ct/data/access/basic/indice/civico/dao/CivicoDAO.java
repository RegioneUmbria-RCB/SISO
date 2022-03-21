package it.webred.ct.data.access.basic.indice.civico.dao;

import it.webred.ct.data.access.basic.indice.civico.dto.RicercaCivicoIndiceDTO;
import it.webred.ct.data.access.basic.indice.dao.IndiceDAOException;
import it.webred.ct.data.model.indice.SitCivicoTotale;

import java.util.List;


public interface CivicoDAO {

	public List<Object[]> getListaCiviciByVia(String id, int start, int rowNumber) throws IndiceDAOException;
	
	public Long getListaCiviciByViaRecordCount(String id) throws IndiceDAOException;
	
	public List<SitCivicoTotale> getListaCivTotaleByViaUnicoDesc(RicercaCivicoIndiceDTO rci)throws IndiceDAOException;
	
	public List<SitCivicoTotale> getListaCivTotaleByCivicoFonte(RicercaCivicoIndiceDTO rci)throws IndiceDAOException;
}
