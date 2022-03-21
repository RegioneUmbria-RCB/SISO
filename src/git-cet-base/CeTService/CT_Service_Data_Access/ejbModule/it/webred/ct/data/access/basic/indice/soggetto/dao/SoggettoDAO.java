package it.webred.ct.data.access.basic.indice.soggetto.dao;

import it.webred.ct.data.access.basic.indice.dao.IndiceDAOException;
import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.data.model.indice.SitSoggettoUnico;
import java.math.BigDecimal;
import java.util.List;

public interface SoggettoDAO {

	public List<SitSoggettoUnico> getListaUnico(int startm, int numberRecord, String sql) throws IndiceDAOException;
	
	public Long getListaUnicoRecordCount(String sql) throws IndiceDAOException;
	
	public List<Object[]> getListaTotale1(String sql, int startm, int numberRecord) throws IndiceDAOException;
	
	public List<Object[]> getListaTotale2(String sql) throws IndiceDAOException;
	
	public Long getListaTotaleRecordCount(String sql) throws IndiceDAOException;
	
	public List<Object[]> getListaTotaleBySorgente(String sql, int startm, int numberRecord) throws IndiceDAOException;	
	
	public List<SitSoggettoTotale> validaSitSoggettoTotale(IndiceOperationCriteria criteria) throws IndiceDAOException;

	public void mergeSoggetto(SitSoggettoTotale sogg) throws IndiceDAOException;

	public List<SitSoggettoTotale> invalidaSitSoggettoTotale(IndiceOperationCriteria criteria) throws IndiceDAOException;
	
	public List<SitSoggettoTotale> getSoggettoTotaleByUnico(BigDecimal idUnicoOld) throws IndiceDAOException;
		
	public int deleteById(long id) throws IndiceDAOException;
	
	public List<SitSoggettoTotale> getSoggettoTotaleByHashByEs(IndiceOperationCriteria criteria) throws IndiceDAOException;
	
	public void persist(SitSoggettoUnico nuovo) throws IndiceDAOException;
	
	public List<SitSoggettoTotale> getSoggettoTotaleNativaByHash(IndiceOperationCriteria criteria) throws IndiceDAOException;
	
	public SitSoggettoUnico getSoggettoUnicoById(BigDecimal id) throws IndiceDAOException;
	

	
}
