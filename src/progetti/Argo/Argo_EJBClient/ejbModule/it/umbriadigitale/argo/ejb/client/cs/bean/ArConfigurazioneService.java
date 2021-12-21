package it.umbriadigitale.argo.ejb.client.cs.bean;

import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArAttivitaDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArOrganizzazioneDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArProgettoDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ProgettiSearchCriteria;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ArConfigurazioneService {

	public List<ArProgettoDTO> getListaProgetti(ProgettiSearchCriteria sc);
	public int countProgetti(ProgettiSearchCriteria sc);
	
	public List<ArOrganizzazioneDTO> getListaOrganizzazioniDTO(String zonaSociale);
	public List<ArOrganizzazioneDTO> getListaOrganizzazioniFuoriZona(String zonaSociale);
	public List<ArAttivitaDTO> getListaAttivita(Long idProgettoSel);
	
	public void salvaProgetto(ArProgettoDTO progettoDto, List<ArOrganizzazioneDTO> orgToRemove) throws Exception;
	public void salvaAttivita(ArAttivitaDTO attivitaDTO) throws Exception;
	public ArOrganizzazioneDTO getOrganizzazioneById(Long idOrganizzazione);
	public boolean existsProgetto(ArProgettoDTO progetto);
	public boolean existsAttivita(ArAttivitaDTO attivita);
	public void eliminaAttivita(Long attivitaId);
	public void eliminaProgetto(Long progettoId);
	public void abilitaProgetti(List<Long> progettiSelezionati);
	public void disabilitaProgetti(List<Long> progettiSelezionati);
	
}
