package it.webred.ct.data.access.basic.indice.via.dao;

import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.dao.IndiceDAOException;
import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;
import it.webred.ct.data.access.basic.indice.dto.SitSorgenteDTO;
import it.webred.ct.data.access.basic.indice.via.dto.SitViaDTO;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.data.model.indice.SitCivicoUnico;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.data.model.indice.SitViaUnico;

import java.math.BigDecimal;
import java.util.List;

public interface ViaDAO {

	public List<SitViaUnico> getListaUnico(int startm, int numberRecord, String sql) throws IndiceDAOException;
	
	public Long getListaUnicoRecordCount(String sql) throws IndiceDAOException;
		
	public List<Object[]> getListaTotale1(String sql, int startm, int numberRecord) throws IndiceDAOException;
	
	public List<Object[]> getListaTotale2(String sql) throws IndiceDAOException;
	
	public Long getListaTotaleRecordCount(String sql) throws IndiceDAOException;
	
	public List<Object[]> getListaTotaleBySorgente(String sql, int startm, int numberRecord) throws IndiceDAOException;	
	
	public List<SitViaTotale> validaSitViaTotale(IndiceOperationCriteria criteria) throws IndiceDAOException;
	
	public void mergeVia(SitViaTotale via) throws IndiceDAOException;
	
	public List<SitCivicoTotale> validaSitCivicoTotale(SitViaTotale via) throws IndiceDAOException;
	
	public void mergeCivico(SitCivicoTotale civico) throws IndiceDAOException;
	
	public List<SitViaTotale> invalidaSitViaTotale(IndiceOperationCriteria criteria) throws IndiceDAOException;
	
	public List<SitCivicoTotale> invalidaSitCivicoTotale(SitViaTotale via) throws IndiceDAOException;
		
	public List<SitViaTotale> getViaTotaleByUnico(BigDecimal idUnicoOld) throws IndiceDAOException;
	
	public List<SitCivicoTotale> getCivicoTotaleByViaTotale(SitViaTotale via) throws IndiceDAOException;
	
	public SitCivicoUnico getCivicoUnicoById(SitCivicoTotale civicoTot) throws IndiceDAOException;
	
	public void mergeCivicoUnico(SitCivicoUnico civUnico) throws IndiceDAOException;
						
	public List<SitViaTotale> getViaTotaleByHashByEs(IndiceOperationCriteria criteria) throws IndiceDAOException;						
	
	public void persist(SitViaUnico nuovo) throws IndiceDAOException;
	
	public void persist(SitCivicoUnico nuovo) throws IndiceDAOException;
	
	public List<SitViaTotale> getViaTotaleNativaByHash(IndiceOperationCriteria criteria) throws IndiceDAOException;

	public int deleteById(long id) throws IndiceDAOException;
	
	public List<SitCivicoUnico> getCivicoUnicoByViaUnico(BigDecimal id) throws IndiceDAOException;
	
	public List<SitCivicoTotale>  getCivicoTotaleByViaUnico(BigDecimal id) throws IndiceDAOException;

	public List<SitViaUnico> getListaViaUnicoByDescr(String toponimo, String via)throws IndiceDAOException;
	
	public List<SitViaTotale> getViaTotaleByUnicoFonte(BigDecimal idUnicoOld, String fonte, String progEs ) throws IndiceDAOException;

	public List<String> getDistinctCivicoUnicoByIdVie(BigDecimal idVia1, BigDecimal idVia2) throws IndiceDAOException;

	public void deleteCivicoUnicoByIdVie(BigDecimal idVia1, BigDecimal idVia2) throws IndiceDAOException;

	public List<SitCivicoTotale> getCivicoTotaleByVieUnicoCivico(BigDecimal idVia1,
			BigDecimal idVia2, String civico) throws IndiceDAOException;
}

