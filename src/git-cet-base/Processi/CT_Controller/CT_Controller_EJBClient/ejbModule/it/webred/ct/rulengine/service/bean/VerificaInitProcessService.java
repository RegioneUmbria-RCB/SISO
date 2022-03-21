package it.webred.ct.rulengine.service.bean;

import it.webred.ct.rulengine.dto.Task;

import javax.ejb.Remote;

@Remote
public interface VerificaInitProcessService {
	
	/**
	 * Verific aprocessi di Reperimento e Acquisizione
	 * @param task
	 * @return
	 */
	public Task verificaInizialeProcesso(Task task);
	
	/**
	 * Verifica processi di trattamento 
	 * @param task
	 * @return
	 */
	public Task verificaInizialeProcessiTrattamento(Task task);
	
	
	/**
	 * Il metodo effettua un controllo sullo stato di eventuali 
	 * processi di trattamentoi su una FD
	 * @param task
	 * @return
	 */
	//public Task isTrattamentoRunning(Task task);
}
