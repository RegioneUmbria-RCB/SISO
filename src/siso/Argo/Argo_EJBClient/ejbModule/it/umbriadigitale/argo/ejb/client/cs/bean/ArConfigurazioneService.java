package it.umbriadigitale.argo.ejb.client.cs.bean;

import it.umbriadigitale.argo.data.ArBiInviante;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArAttivitaDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArFondoDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArFonteDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArOrganizzazioneDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArProgettoDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ProgettiSearchCriteria;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ArConfigurazioneService {

	public List<ArProgettoDTO> getListaProgetti(List<Long> idOrganizzazioni);
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
	
	public List<ArFonteDTO> getListaFonti(ProgettiSearchCriteria sc);
	public int countFonti(ProgettiSearchCriteria sc);
	public boolean existsFonteFinanziamento(ArFonteDTO selectedFonte);
	public void salvaFonte(ArFonteDTO selectedFonte, List<ArOrganizzazioneDTO> toRemove);
	public void eliminaFonte(Long id);
	public void abilitaFonte(List<Long> lstIds);
	public List<ArFondoDTO> getListaFondiDTO();
	public void disabilitaFonti(List<Long> lstIds);
	
	// SISO-1160
	public String findCodRoutingInviante(String nomeInviante, Long idInviante);
 
}
