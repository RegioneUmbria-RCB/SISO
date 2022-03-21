package it.webred.ct.rulengine.service.bean;

import java.util.List;

import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.controller.model.RProcessMonitorPK;
import it.webred.ct.rulengine.dto.Task;


import javax.ejb.Remote;

@Remote
public interface ProcessMonitorService {
	
	public void saveProcessInstance(RProcessMonitor rProcessMonitor);
	
	public void saveOrUpdateProcessInstance(RProcessMonitor rProcessMonitor);
	
	public RProcessMonitor getProcessMonitor(RProcessMonitorPK rProcessMonitorPK);
	
	public RProcessMonitor getProcessMonitorByProcessId(String processId);
	
	public List<RProcessMonitor> getLockedProcessMonitor(String belfiore,Long idFonte); 
	
	public List<RProcessMonitor> getPrevProcessMonitor(String belfiore, Long idFonte);
	
	/**
	 * Il metodo effettua un controllo sullo stato di eventuali 
	 * processi di trattamento su una FD
	 * @param task
	 * @return
	 */
	public Task isTrattamentoRunning(Task task);
}
