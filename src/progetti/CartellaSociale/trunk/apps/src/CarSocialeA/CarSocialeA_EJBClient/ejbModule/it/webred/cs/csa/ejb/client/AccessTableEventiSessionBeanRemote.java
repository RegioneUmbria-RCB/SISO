package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.EventoDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableEventiSessionBeanRemote {
	
	public void creaEvento(EventoDTO nuovoEventoDTO);
	
	/**
	 * Ritorna un singolo EventoDTO 
	 * @param idEvento
	 * @return
	 */
	public EventoDTO findEventoByID(Long idEvento);
	
	/***
	 * Ritorna la lista di tutti gli eventi
	 * @return
	 */
	public List<EventoDTO> findAllEvents(BaseDTO bdto);
	
	public void cancellaEvento(EventoDTO eventoDTO);
	
	/**
	 * Ritorna la lista di eventi contenente operatoreSettoreID e CF da parametro 
	 * @param CF
	 * @param opSettId
	 * @return
	 */
	public List<EventoDTO>findEventsByOpSettIdAndCF(BaseDTO dto);
}
