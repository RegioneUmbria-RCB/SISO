package it.webred.ct.data.access.basic.indice;


import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;
import it.webred.ct.data.access.basic.indice.dto.SitNuovoDTO;
import it.webred.ct.data.model.indice.SitEnteSorgente;
import it.webred.ct.data.model.indice.SitViaUnico;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface IndiceService{

	//public List<SitEnteSorgente> getListaEntiSorgenti();	
	public List<SitEnteSorgente> getListaEntiSorgenti(IndiceDataIn indDataIn);
	
	public SitEnteSorgente getEnteSorgente(IndiceDataIn indDataIn);
	
	//public List getListaUnico(IndiceSearchCriteria criteria, int startm, int numberRecord);
	public List getListaUnico(IndiceDataIn indDataIn);
	
	public Long getListaUnicoRecordCount(IndiceSearchCriteria criteria);	
	
	//public List getListaTotale(IndiceSearchCriteria criteria, int startm, int numberRecord);
	public List getListaTotale(IndiceDataIn indDataIn);
	
	public Long getListaTotaleRecordCount(IndiceSearchCriteria criteria);
	
	//public List getListaTotaleBySorgente(IndiceSearchCriteria criteria, int startm, int numberRecord);
	public List getListaTotaleBySorgente(IndiceDataIn indDataIn);
	
	public Long getListaTotaleBySorgenteRecordCount(IndiceSearchCriteria criteria);
	
	public void validaSitTotale(IndiceOperationCriteria criteria);
	
	public void invalidaSitTotale(IndiceOperationCriteria criteria);
	
	//public void cambiaUnico(IndiceOperationCriteria criteria, String nuovoIdUnico);
	public void cambiaUnico(IndiceDataIn indDataIn);
	
	public void associaANuovoUnico(IndiceOperationCriteria criteria);
	
	//public void aggregaUnici(BigDecimal idUno, BigDecimal idDue, SitNuovoDTO nuovoUnico);
	public void aggregaUnici(IndiceDataIn indDataIn);
	
	public BigDecimal getUnicoDaNativoTotale(IndiceOperationCriteria criteria);
	
	//public void cancellaUnicoById(long id);
	public void cancellaUnicoById(IndiceDataIn indDataIn);
}
