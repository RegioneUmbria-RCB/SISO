package it.webred.cs.csa.ejb.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableEventiSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.EventoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.EventoDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.model.CsAImportExport;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreSettore;

@Stateless
public class AccessTableEventiSessionBean extends CarSocialeBaseSessionBean
		implements AccessTableEventiSessionBeanRemote {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EventoDAO eventoDao;

	@Override
	public void creaEvento(EventoDTO nuovoEventoDTO) {
		CsAImportExport nuovoEvento = new CsAImportExport();

		nuovoEvento.setTipoEvento(nuovoEventoDTO.getTipoEvento());
		nuovoEvento.setMittente(nuovoEventoDTO.getMittente());
		nuovoEvento.setDestinatario(nuovoEventoDTO.getDestinatario());
		nuovoEvento.setCognome(nuovoEventoDTO.getCognomeSoggetto());
		nuovoEvento.setNome(nuovoEventoDTO.getNomeSoggetto());
		nuovoEvento.setCodiceFiscale(nuovoEventoDTO.getCodiceFiscale());
		// Operatore
		CsOOperatoreSettore operatoreSettore = new CsOOperatoreSettore();
		operatoreSettore.setId(nuovoEventoDTO.getOperatore()
				.getIdOperatoreSettore());
		nuovoEvento.setOperatoreSettore(operatoreSettore);
		// Inserisco la data odierna
		nuovoEvento.setDataEvento(new Date());
		nuovoEvento.setCartellaSociale(nuovoEventoDTO.getCartellaSociale());

		eventoDao.creaEvento(nuovoEvento);
	}

	/**
	 * Ritorna un singolo EventoDTO
	 * 
	 * @param idEvento
	 * @return
	 */
	@Override
	public EventoDTO findEventoByID(Long idEvento) {
		EventoDTO evento = new EventoDTO();

		CsAImportExport csaEvento = eventoDao.findCasoById(idEvento);

		evento.setCodiceFiscale(csaEvento.getCodiceFiscale());
		evento.setCognomeSoggetto(csaEvento.getCognome());
		return evento;
	}

	/***
	 * Ritorna la lista di tutti gli eventi
	 * 
	 * @return
	 */
	@Override
	public List<EventoDTO> findAllEvents(BaseDTO bdto) {
		List<CsAImportExport> csaEventi = eventoDao.findAll();

		List<EventoDTO> eventi = new ArrayList<EventoDTO>();

		for (CsAImportExport csaEvento : csaEventi) {
			eventi.add(this.convertToEventDTO(csaEvento));
		}

		return eventi;
	}
	
	@Override
	public List<EventoDTO> findEventsByOpSettIdAndCF(String CF, long orgID, String type){

		List<CsAImportExport> csaEventi = eventoDao.findAll();
		

		List<EventoDTO> eventi = new ArrayList<EventoDTO>();
		
		for (CsAImportExport csaEvento : csaEventi) {
			/**
			 * Controllo che il CF dell'evento coincida con il CF del caso e che appartengano alla stessa organizzazione 
			 */
			if(csaEvento.getCodiceFiscale().equals(CF) && csaEvento.getOperatoreSettore().getCsOSettore().getCsOOrganizzazione().getId() == orgID  && csaEvento.getTipoEvento().equals(type)){
				eventi.add(this.convertToEventDTO(csaEvento));
			}
		}
		
		return eventi;
	}

	@Override
	public void cancellaEvento(EventoDTO eventoDTO) {
		CsAImportExport csaEvento = eventoDao.findCasoById(eventoDTO.getId());

		// Cancellazione
		csaEvento.setArchiviato(true);
		csaEvento.setDataCancellazione(new Date());

		this.eventoDao.cancellaEvento(csaEvento);

	}
	
	private EventoDTO convertToEventDTO(CsAImportExport csAEvento){

			// Creo OperatoreDTO da aggiungere ad EventoDTO
			OperatoreDTO operatore = new OperatoreDTO();
			operatore.setIdOperatoreSettore(csAEvento.getOperatoreSettore()
					.getId());
			operatore.setUsername(csAEvento.getOperatoreSettore()
					.getCsOOperatore().getUsername());

			EventoDTO evt = new EventoDTO(csAEvento.getId(),
					csAEvento.getTipoEvento(), csAEvento.getMittente(),
					csAEvento.getDestinatario(), csAEvento.getNome(),
					csAEvento.getCognome(), csAEvento.getCodiceFiscale(),
					csAEvento.getCartellaSociale(), csAEvento.getDataEvento(),
					operatore);

			CsOOperatoreAnagrafica anaOperatore = csAEvento.getOperatoreSettore()
					.getCsOOperatore().getCsOOperatoreAnagrafica();
			
			evt.setNomeCognomeOperatore(anaOperatore.getCognome() + " " + anaOperatore.getNome()); 
			
			return evt;
	}
}
