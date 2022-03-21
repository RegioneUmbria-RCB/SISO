package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.alert.AlertDTO;
import it.webred.cs.csa.ejb.dto.alert.TipoAlertBean;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableAlertSessionBeanRemote {
	
	public LinkedList<TipoAlertBean> getNotificheVisibili(BaseDTO dto)throws Exception;
	
	public CsAlert findAlertById(IterDTO dto)throws Exception;
	
	public List<CsAlert> findAlertByIdCasoTipo(IterDTO dto)throws Exception;
	
	public boolean validaInserimentoNuovoAlert(BaseDTO dto) throws Exception; 
	
	public void updateLettoById(IterDTO dto)throws Exception;

	public void updatePulisciLista(IterDTO dto)throws Exception;

	public void updateLeggiAll(IterDTO dto)throws Exception;
	
	public void updateAlert(BaseDTO dto);
	
	public CsOOrganizzazione findAlertOrganizzazioneDefault(BaseDTO dto) throws Exception;

	public void addAlert(AlertDTO dto) throws CarSocialeServiceException;
	
	public void addAlertNuovoInserimentoToResponsabileCaso(BaseDTO dto) throws CarSocialeServiceException;//SISO-1278
	
	public List<CsAlert> findAlertEmail(BaseDTO bDto);
	public boolean isRicEmailAttiva(BaseDTO bDto);

	/*TIMERTASK*/
	public void aggiornaAlertPAI(CeTBaseObject cet) throws Exception;

	public CsOOperatoreSettore findDestinatarioAlertCaso(BaseDTO dto);
	
	

}
