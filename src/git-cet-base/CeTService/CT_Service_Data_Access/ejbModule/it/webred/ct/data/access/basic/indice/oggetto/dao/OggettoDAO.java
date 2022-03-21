package it.webred.ct.data.access.basic.indice.oggetto.dao;

import it.webred.ct.data.access.basic.indice.dao.IndiceDAOException;
import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.model.indice.SitOggettoTotale;
import it.webred.ct.data.model.indice.SitOggettoUnico;
import java.math.BigDecimal;
import java.util.List;

public interface OggettoDAO {

	public List<SitOggettoUnico> getListaUnico(int startm, int numberRecord, String sql) throws IndiceDAOException;
	
	public Long getListaUnicoRecordCount(String sql) throws IndiceDAOException;
	
	public List<Object[]> getListaTotale1(String sql, int startm, int numberRecord) throws IndiceDAOException;
	
	public List<Object[]> getListaTotale2(String sql) throws IndiceDAOException;
	
	public Long getListaTotaleRecordCount(String sql) throws IndiceDAOException;
	
	public List<Object[]> getListaTotaleBySorgente(String sql, int startm, int numberRecord) throws IndiceDAOException;	
	
	public List<SitOggettoTotale> validaSitOggettoTotale(IndiceOperationCriteria criteria) throws IndiceDAOException;

	public void mergeOggetto(SitOggettoTotale ogg) throws IndiceDAOException;

	public List<SitOggettoTotale> invalidaSitOggettoTotale(IndiceOperationCriteria criteria) throws IndiceDAOException;
	
	public List<SitOggettoTotale> getOggettoTotaleByUnico(BigDecimal idUnicoOld) throws IndiceDAOException;
		
	public int deleteById(long id) throws IndiceDAOException;
	
	public List<SitOggettoTotale> getOggettoTotaleByHashByEs(IndiceOperationCriteria criteria) throws IndiceDAOException;
	
	public void persist(SitOggettoUnico nuovo) throws IndiceDAOException;
	
	public List<SitOggettoTotale> getOggettoTotaleNativaByHash(IndiceOperationCriteria criteria) throws IndiceDAOException;
	
	public SitOggettoUnico getOggettoUnicoById(BigDecimal id) throws IndiceDAOException;
	
}
