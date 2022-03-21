package it.webred.ct.rulengine.web.bean.queue;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.rulengine.controller.model.RCoda;
import it.webred.ct.rulengine.dto.CodaDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

public class QueueBean extends QueueBaseBean implements Serializable {

	private static Logger logger = Logger.getLogger(QueueBean.class.getName());

	private List<CodaDTO> listaCoda = new ArrayList<CodaDTO>();
	private List<SelectItem> listaEnti = new ArrayList<SelectItem>();
	private List<String> listaEntiAuth = new ArrayList<String>();

	private String enteSelezionato;
	private Long rCodaId;

	@PostConstruct
	public void init() {
		
		logger.debug("Inizializzazione Queue");
		List<AmComune> listaComuni = getListaEntiAuth();
		for (AmComune comune : listaComuni) {
			listaEntiAuth.add(comune.getBelfiore());
			listaEnti.add(new SelectItem(comune.getBelfiore(), comune
					.getDescrizione()));
		}
		doCaricaListaQueue();
		
	}
	
	public String goQueue() {

		return "controller.queue";
		
	}

	public void doCaricaListaQueue() {

		listaCoda = new ArrayList<CodaDTO>();
		List<RCoda> lista = new ArrayList<RCoda>();

		logger.debug("Caricamento lista coda");

		try {

			if (enteSelezionato == null || "".equals(enteSelezionato))
				lista = queueService.getRCodaByEnti(listaEntiAuth);
			else
				lista = queueService.getRCodaByEnte(enteSelezionato);

			for (RCoda coda : lista) {

				CodaDTO dto = new CodaDTO();
				dto.setrCoda(coda);
				dto.setEnte(comuneService.getComune(coda.getBelfiore())
						.getDescrizione());
				Calendar calendar = Calendar.getInstance();
				if (coda.getInizioEsecuzione() != null) {
					calendar.setTimeInMillis(coda.getInizioEsecuzione());
					dto.setInizioEsec(calendar.getTime());
				}

				listaCoda.add(dto);

			}

		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
			super.addErrorMessage("queue.lista.error", e.getMessage());
		}

	}

	public void doDeleteQueue() {
		logger.info("Eliminazione elemento rCoda");
		String msg = "queue.delete";

		try {

			RCoda coda = new RCoda();
			coda.setIstante(rCodaId);
			queueService.deleteProcess(coda);

			super.addInfoMessage(msg);

			// update
			doCaricaListaQueue();
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}
	}

	public String getEnteSelezionato() {
		return enteSelezionato;
	}

	public void setEnteSelezionato(String enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}

	public List<CodaDTO> getListaCoda() {
		return listaCoda;
	}

	public void setListaCoda(List<CodaDTO> listaCoda) {
		this.listaCoda = listaCoda;
	}

	public Long getrCodaId() {
		return rCodaId;
	}

	public void setrCodaId(Long rCodaId) {
		this.rCodaId = rCodaId;
	}

	public List<SelectItem> getListaEnti() {
		return listaEnti;
	}

	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}

}
