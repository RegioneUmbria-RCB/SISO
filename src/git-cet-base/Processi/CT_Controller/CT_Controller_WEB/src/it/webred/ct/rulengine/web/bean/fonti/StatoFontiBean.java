package it.webred.ct.rulengine.web.bean.fonti;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.data.access.basic.common.CommonDataIn;
import it.webred.ct.data.access.basic.fonti.FontiDataIn;
import it.webred.ct.data.access.basic.fonti.dto.FontiDTO;
import it.webred.ct.data.access.basic.processi.ProcessiDataIn;
import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.controller.model.RProcessMonitorPK;
import it.webred.ct.rulengine.controller.model.RTracciaDate;
import it.webred.ct.rulengine.controller.model.RTracciaDatePK;
import it.webred.ct.rulengine.controller.model.RTracciaStati;
import it.webred.ct.rulengine.controller.model.RTracciaStatiPK;
import it.webred.ct.rulengine.dto.StatoFontiDTO;
import it.webred.ct.rulengine.dto.StatoFontiDettDTO;
import it.webred.ct.rulengine.dto.Task;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

public class StatoFontiBean extends FontiBaseBean implements Serializable {

	private static Logger logger = Logger.getLogger(StatoFontiBean.class
			.getName());

	private String enteSelezionato;
	private Long fonteSelezionata;
	private Long istanteSelezionato;
	private Date dataSelezionata;
	private Long statoSelezionato;

	private List<SelectItem> listaEnti = new ArrayList<SelectItem>();
	private List<SelectItem> listaStati = new ArrayList<SelectItem>();

	private List<StatoFontiDTO> listaStatoInt = new ArrayList<StatoFontiDTO>();
	private List<StatoFontiDTO> listaStatoExt = new ArrayList<StatoFontiDTO>();
	private List<StatoFontiDettDTO> listaStatoDettaglio = new ArrayList<StatoFontiDettDTO>();

	private RTracciaStati newTracciaStati;
	private RTracciaDate rTracciaDate;

	private String newStatoFontiPage = "/jsp/protected/empty.xhtml";
	private String dettStatoFontiPage = "/jsp/protected/empty.xhtml";
	private String dateFontiPage = "/jsp/protected/empty.xhtml";
	
	private RTracciaStati tracciaCorrente;

	@PostConstruct
	public void init() {

		logger.debug("Inizializzazione");
		List<AmComune> listaComuni = getListaEntiAuth();
		for (AmComune comune : listaComuni) {
			listaEnti.add(new SelectItem(comune.getBelfiore(), comune
					.getDescrizione()));
		}
		if (listaEnti.size() > 0) {
			enteSelezionato = (String) listaEnti.get(0).getValue();
		}
		
	}

	public void doCaricaStatoFonti() {

		listaStatoInt = new ArrayList<StatoFontiDTO>();
		listaStatoExt = new ArrayList<StatoFontiDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

		try {

			Calendar c = Calendar.getInstance();
			List<AmFonteComune> listaFonti = comuneService
					.getListaFonteByComune(enteSelezionato);
			for (AmFonteComune fc : listaFonti) {

				StatoFontiDTO dto = new StatoFontiDTO();

				dto.setFonte(fc.getAmFonte().getDescrizione());
				dto.setIdFonte(new Long(fc.getId().getFkAmFonte()));
				Date min = tracciaStatiService.getMinTracciaStato(
						enteSelezionato, new Long(fc.getId().getFkAmFonte()));
				if(min != null)
					dto.setDataInizio(sdf.format(min));
				List<RTracciaStati> listaTraccia = tracciaStatiService
						.getTracciaStato(enteSelezionato, new Long(fc.getId()
								.getFkAmFonte()));
				if (listaTraccia.size() > 0) {
					RTracciaStati ultimaTraccia = listaTraccia.get(0);
					c.setTimeInMillis(ultimaTraccia.getId().getIstante());
					dto.setDataAgg(sdf.format(c.getTime()));
					dto.setrTracciaStati(ultimaTraccia);
				}
				
				//recupero le date da rtracciadate, se non esiste eseguo la query per quella fonte
				RTracciaDate date = tracciaStatiService.getTracciaDate(enteSelezionato, new Long(fc.getId().getFkAmFonte()));
				if(date != null){
					dto.setDataRifInizio(date.getDatamin()!=null?sdf2.format(date.getDatamin()):null);
					dto.setDataRifAgg(date.getDatamax()!=null?sdf2.format(date.getDatamax()):null);
				}else{
					// il metodo getDateRiferimentoFonte preleva il property 
					// per le date delle fonti dati ed esegue la native query
					FontiDataIn fontiDataIn = new FontiDataIn();
					fontiDataIn.setEnteId(enteSelezionato);
					fontiDataIn.setIdFonte(fc.getId().getFkAmFonte().toString());
					FontiDTO fontiDTO = fontiService.getDateRiferimentoFonte(fontiDataIn);
					if(fontiDTO.getDataRifInizio() != null)
						dto.setDataRifInizio(sdf2.format(fontiDTO.getDataRifInizio()));
					if(fontiDTO.getDataRifAggiornamento() != null)
						dto.setDataRifAgg(sdf2.format(fontiDTO.getDataRifAggiornamento()));
				}
				
				if (fc.getAmFonte().getTipoFonte().equals("I"))
					listaStatoInt.add(dto);
				else
					listaStatoExt.add(dto);

			}

		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
			super.addErrorMessage("statofonti.load.error", e.getMessage());
		}
	}

	public void doCaricaStatoFontiDettaglio() {
		logger.info("Carico dettaglio");
		String msg = "statofonti.dettaglio";
		listaStatoDettaglio = new ArrayList<StatoFontiDettDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try {

			List<RTracciaStati> listaTracciaStati = tracciaStatiService.getTracciaStato(enteSelezionato, fonteSelezionata);
			for(RTracciaStati ts: listaTracciaStati){
				
				StatoFontiDettDTO dto = new StatoFontiDettDTO();
				dto.setrTracciaStati(ts);
				dto.setDataAgg(sdf.format(new Date(ts.getId().getIstante())));
				if(ts.getProcessid() != null){
					
					ProcessiDataIn dataIn = new ProcessiDataIn();
					dataIn.setProcessId(ts.getProcessid());
					dataIn.setEnteId(enteSelezionato);
					dto.setListaSintesiProcessi(processiService.getSintesiprocessiByProcessId(dataIn));
					dto.setListaForniture(tracciaStatiService.getTracciaFornitureByProcessId(ts.getProcessid()));
				}
				
				listaStatoDettaglio.add(dto);
			}
			
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}

	}
	
	public void doCaricaStatoFontiDate() {
		logger.info("Carico date");
		String msg = "statofonti.date";
		listaStatoDettaglio = new ArrayList<StatoFontiDettDTO>();
		
		try {

			rTracciaDate = tracciaStatiService.getTracciaDate(enteSelezionato, fonteSelezionata);
			if(rTracciaDate == null){
				rTracciaDate = new RTracciaDate();
			}
			
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}
	}
	
	public void doDeleteTracciaStati(){
		logger.info("Eliminazione traccia stato");
		String msg = "statofonti.elimina";

		try {
			
			RTracciaStati rTracciaStati = new RTracciaStati();
			RTracciaStatiPK pk = new RTracciaStatiPK();
			pk.setBelfiore(enteSelezionato);
			pk.setIdFonte(fonteSelezionata);
			pk.setIstante(istanteSelezionato);
			rTracciaStati.setId(pk);
			tracciaStatiService.deleteTracciaStato(rTracciaStati);
			
			super.addInfoMessage(msg);

			// update
			doCaricaStatoFontiDettaglio();
			doCaricaStatoFonti();
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}
		
	}
	
	public void doDeleteTracciaDate(){
		logger.info("Eliminazione traccia date");
		String msg = "statofonti.salva";

		try {
			
			RTracciaDate rTracciaDate = new RTracciaDate();
			RTracciaDatePK pk = new RTracciaDatePK();
			pk.setBelfiore(enteSelezionato);
			pk.setIdFonte(fonteSelezionata);
			rTracciaDate.setId(pk);
			tracciaStatiService.deleteTracciaDate(rTracciaDate);
			
			super.addInfoMessage(msg);

			// update
			doCaricaStatoFonti();
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}
		
	}

	public void doSalvaTracciaStato() {
		logger.info("Salvataggio traccia stato");
		String msg = "statofonti.salva";

		try {

			RTracciaStatiPK pk = new RTracciaStatiPK();
			pk.setBelfiore(enteSelezionato);
			pk.setIdFonte(fonteSelezionata);
			pk.setIstante(dataSelezionata.getTime());
			newTracciaStati.setId(pk);
			tracciaStatiService.saveTracciaStato(newTracciaStati);
			
			//aggiornamento monitor processi
			Task t = new Task(enteSelezionato,fonteSelezionata,newTracciaStati.getRAnagStati().getRCommandType().getId());
			RCommand rc = recuperaComandoService.getRCommand(t);
			
			if(rc != null) {
				RProcessMonitor rpm = new RProcessMonitor();
				rpm.setId(new RProcessMonitorPK(enteSelezionato,rc.getId()));
				rpm.setIstante(dataSelezionata.getTime());
				rpm.setRCommand(rc);
				rpm.setRAnagStato(newTracciaStati.getRAnagStati());
				processMonitorService.saveOrUpdateProcessInstance(rpm);
				logger.info("Processo aggiornato nel monitor");
			}
			else {
				logger.warn("Processo non previsto per l'ente");
			}
			
			resetPage();
			super.addInfoMessage(msg);

			// update
			doCaricaStatoFonti();
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}

	}
	
	public void doAggiornaNote() {
		logger.info("Aggiornamento note");
		String msg = "statofonti.salva";

		try {

			tracciaStatiService.saveOrUpdateTracciaStato(tracciaCorrente);
			
			resetPage();
			super.addInfoMessage(msg);

			// update
			doCaricaStatoFonti();
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}
	}

	public void doSalvaTracciaDate() {
		logger.info("Salvataggio traccia date");
		String msg = "statofonti.salva";

		try {
			
			if(rTracciaDate.getId() == null){
				rTracciaDate.setId(new RTracciaDatePK(enteSelezionato, fonteSelezionata));
				tracciaStatiService.saveTracciaDate(rTracciaDate);
			}else
				tracciaStatiService.updateTracciaDate(rTracciaDate);
			
			resetPage();
			super.addInfoMessage(msg);

			// update
			doCaricaStatoFonti();
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}

	}
	
	public void doInitNew() {

		try {
			tracciaCorrente = null;
			newTracciaStati = new RTracciaStati();
			dataSelezionata = new Date();
			RAnagStati st = mainControllerService.getRAnagStato(new Long(10),
					"F");
			newTracciaStati.setNote(st.getDescr());
			newTracciaStati.setRAnagStati(st);

			if (listaStati.size() == 0) {
				listaStati.add(new SelectItem(st.getId(), st.getDescr()));
				st = mainControllerService.getRAnagStato(new Long(10), "E");
				listaStati.add(new SelectItem(st.getId(), st.getDescr()));
				st = mainControllerService.getRAnagStato(new Long(20), "F");
				listaStati.add(new SelectItem(st.getId(), st.getDescr()));
				st = mainControllerService.getRAnagStato(new Long(20), "E");
				listaStati.add(new SelectItem(st.getId(), st.getDescr()));
			}

		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
		}

	}
	
	public void doInitUpdate() {
		try {
			List<RTracciaStati> listaTracciaStati = tracciaStatiService.getTracciaStato(enteSelezionato, fonteSelezionata);
			tracciaCorrente = listaTracciaStati.get(0);
		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
		}
	}

	public void doCaricaNote() {

		try {
			RAnagStati st = mainControllerService
					.getRAnagStato(statoSelezionato);
			newTracciaStati.setNote(st.getDescr());
			newTracciaStati.setRAnagStati(st);
		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
			super.addErrorMessage("statofonti.load.error", e.getMessage());
		}

	}

	public String goStatoFonti() {

		doCaricaStatoFonti();
		return "controller.statofonti";
	}

	public void resetPage() {

		newStatoFontiPage = "/jsp/protected/empty.xhtml";
		dettStatoFontiPage = "/jsp/protected/empty.xhtml";
		dateFontiPage = "/jsp/protected/empty.xhtml";

	}

	public List<StatoFontiDTO> getListaStatoInt() {
		return listaStatoInt;
	}

	public void setListaStatoInt(List<StatoFontiDTO> listaStatoInt) {
		this.listaStatoInt = listaStatoInt;
	}

	public List<StatoFontiDTO> getListaStatoExt() {
		return listaStatoExt;
	}

	public void setListaStatoExt(List<StatoFontiDTO> listaStatoExt) {
		this.listaStatoExt = listaStatoExt;
	}

	public String getEnteSelezionato() {
		return enteSelezionato;
	}

	public void setEnteSelezionato(String enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}

	public List<SelectItem> getListaEnti() {
		return listaEnti;
	}

	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}

	public Long getFonteSelezionata() {
		return fonteSelezionata;
	}

	public void setFonteSelezionata(Long fonteSelezionata) {
		this.fonteSelezionata = fonteSelezionata;
	}

	public Long getIstanteSelezionato() {
		return istanteSelezionato;
	}

	public void setIstanteSelezionato(Long istanteSelezionato) {
		this.istanteSelezionato = istanteSelezionato;
	}

	public String getNewStatoFontiPage() {
		return newStatoFontiPage;
	}

	public void setNewStatoFontiPage(String editStatoFontiPage) {
		this.newStatoFontiPage = editStatoFontiPage;
	}

	public RTracciaStati getNewTracciaStati() {
		return newTracciaStati;
	}

	public void setNewTracciaStati(RTracciaStati newTracciaStati) {
		this.newTracciaStati = newTracciaStati;
	}

	public Date getDataSelezionata() {
		return dataSelezionata;
	}

	public void setDataSelezionata(Date dataSelezionata) {
		this.dataSelezionata = dataSelezionata;
	}

	public List<SelectItem> getListaStati() {
		return listaStati;
	}

	public void setListaStati(List<SelectItem> listaStati) {
		this.listaStati = listaStati;
	}

	public Long getStatoSelezionato() {
		return statoSelezionato;
	}

	public void setStatoSelezionato(Long statoSelezionato) {
		this.statoSelezionato = statoSelezionato;
	}

	public String getDettStatoFontiPage() {
		return dettStatoFontiPage;
	}

	public void setDettStatoFontiPage(String dettStatoFontiPage) {
		this.dettStatoFontiPage = dettStatoFontiPage;
	}

	public List<StatoFontiDettDTO> getListaStatoDettaglio() {
		return listaStatoDettaglio;
	}

	public void setListaStatoDettaglio(List<StatoFontiDettDTO> listaStatoDettaglio) {
		this.listaStatoDettaglio = listaStatoDettaglio;
	}

	public RTracciaStati getTracciaCorrente() {
		return tracciaCorrente;
	}

	public void setTracciaCorrente(RTracciaStati tracciaCorrente) {
		this.tracciaCorrente = tracciaCorrente;
	}

	public RTracciaDate getrTracciaDate() {
		return rTracciaDate;
	}

	public void setrTracciaDate(RTracciaDate rTracciaDate) {
		this.rTracciaDate = rTracciaDate;
	}

	public String getDateFontiPage() {
		return dateFontiPage;
	}

	public void setDateFontiPage(String dateFontiPage) {
		this.dateFontiPage = dateFontiPage;
	}

}
