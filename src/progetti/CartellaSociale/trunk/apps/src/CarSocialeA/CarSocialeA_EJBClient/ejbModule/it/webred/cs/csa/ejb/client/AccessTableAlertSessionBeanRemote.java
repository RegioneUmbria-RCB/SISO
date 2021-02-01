package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.data.model.CsAlertBASIC;
import it.webred.cs.data.model.CsAlertConfig;
import it.webred.cs.data.model.persist.CsAlert;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableAlertSessionBeanRemote {
	
	
	public List<CsAlertBASIC> getNotificas(IterDTO dto)throws Exception;
	
	public CsAlert findAlertById(IterDTO dto)throws Exception;
	
	public List<CsAlert> findAlertByIdCasoTipo(IterDTO dto)throws Exception;
	
	public List<CsAlert> findAlertVisibiliByIdCasoTipoOpeTo(IterDTO dto)throws Exception;

	public void updateLettoById(IterDTO dto)throws Exception;

	public void updatePulisciLista(IterDTO dto)throws Exception;

	public void updateLeggiAll(IterDTO dto)throws Exception;
	
	public void updateAlert(BaseDTO dto);

	public CsAlertConfig getAlertConfigByTipo(BaseDTO dto) throws Exception;

	public void addAlert(AlertDTO dto) throws Exception;
	
	public void addAlertNuovoInserimentoToResponsabileCaso(BaseDTO dto) throws Exception;//SISO-1278
	
	public List<CsAlert> findAlertEmail(BaseDTO bDto);
	public boolean isRicEmailAttiva(BaseDTO bDto);
	public boolean isRicNotificaAttiva(BaseDTO bDto);

	

}
