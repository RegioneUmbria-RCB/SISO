package it.webred.ct.rulengine.web.bean.scheduler;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.rulengine.controller.model.RCommandLaunch;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.controller.model.RSchedulerTime;
import it.webred.ct.rulengine.controller.model.RTracciaStati;
import it.webred.ct.rulengine.dto.LogDTO;
import it.webred.ct.rulengine.dto.SchedulerDTO;

import org.apache.log4j.Logger;

public class SMonitorBean extends SchedulerBaseBean implements Serializable {

	private static Logger logger = Logger.getLogger(SMonitorBean.class
			.getName());

	private List<SchedulerDTO> listaSchedulerDTOAuto = new ArrayList<SchedulerDTO>();
	private List<SchedulerDTO> listaSchedulerDTOManual = new ArrayList<SchedulerDTO>();
	private List<SchedulerDTO> listaSchedulerDTOScaduti = new ArrayList<SchedulerDTO>();
	private List<SelectItem> listaEnti = new ArrayList<SelectItem>();
	private List<String> listaEntiAuth = new ArrayList<String>();
	private List<LogDTO> listaLog = new ArrayList<LogDTO>();

	private String enteSelezionato;
	private Long rSchedulerTimeId;
	private String statolistaSchedulerDTOScaduti;

	private String schedulerLogPage = "/jsp/protected/empty.xhtml";

	public String goScheduler() {

		logger.debug("Inizializzazione Scheduler");
		if (listaEnti.size() == 0) {
			List<AmComune> listaComuni = getListaEntiAuth();
			for (AmComune comune : listaComuni) {
				listaEntiAuth.add(comune.getBelfiore());
				listaEnti.add(new SelectItem(comune.getBelfiore(), comune
						.getDescrizione()));
			}
		}
		doCaricaListaSchedulerTime();
		return "controller.scheduler";
	}

	public void doCaricaListaSchedulerTime() {

		listaSchedulerDTOAuto = new ArrayList<SchedulerDTO>();
		listaSchedulerDTOManual = new ArrayList<SchedulerDTO>();
		boolean isSincronized = true; 
		
		logger.debug("Caricamento lista scheduler time");

		try {

			List<RSchedulerTime> listaRTime = new ArrayList<RSchedulerTime>();
			if (enteSelezionato == null || "".equals(enteSelezionato))
				listaRTime = schedulerTimeService.getSchedulerProcesses(listaEntiAuth);
			else
				listaRTime = schedulerTimeService
						.getSchedulerProcesses(enteSelezionato);

			List<String> listaJobs = quartzService.getJobs();
			for (RSchedulerTime rst : listaRTime) {

				SchedulerDTO dto = new SchedulerDTO();
				dto.setrScheduler(rst);
				dto.setEnte(comuneService.getComune(rst.getBelfiore())
						.getDescrizione());

				List<RFontedatiCommand> fontiDati = recuperaComandoService
						.getRCommandFDs(rst.getRCommand());
				String stringaFonteDati = "";
				if (fontiDati.size() > 0) {
					for (RFontedatiCommand fontedatiCommand : fontiDati) {

						AmFonteComune fc = comuneService
								.getFonteComuneByComuneFonte(rst.getBelfiore(),
										fontedatiCommand.getId().getIdFonte()
												.intValue());
						if (fc != null) {
							stringaFonteDati += stringaFonteDati.equals("") ? fc
									.getAmFonte().getDescrizione()
									: "; " + fc.getAmFonte().getDescrizione();
						}

					}
				}
				dto.setFonte(stringaFonteDati);

				String intervallo = "";
				int campoIntervallo = 0;
				int amountIntervallo = 0;
				if (rst.getOgniGiorni() != null) {
					intervallo = rst.getOgniGiorni().toString()
							+ (new Long(1).equals(rst.getOgniGiorni()) ? " giorno"
									: " giorni");
					campoIntervallo = Calendar.DAY_OF_YEAR;
					amountIntervallo = rst.getOgniGiorni().intValue();
				} else if (rst.getOgniMesi() != null) {
					intervallo = rst.getOgniMesi().toString()
							+ (new Long(1).equals(rst.getOgniMesi()) ? " mese"
									: " mesi");
					campoIntervallo = Calendar.MONTH;
					amountIntervallo = rst.getOgniMesi().intValue();
				} else if (rst.getOgniOre() != null) {
					intervallo = rst.getOgniOre().toString()
							+ (new Long(1).equals(rst.getOgniOre()) ? " ora"
									: " ore");
					campoIntervallo = Calendar.HOUR;
					amountIntervallo = rst.getOgniOre().intValue();
				} else if (rst.getOgniSettimane() != null) {
					intervallo = rst.getOgniSettimane().toString()
							+ (new Long(1).equals(rst.getOgniSettimane()) ? " settimana"
									: " settimane");
					campoIntervallo = Calendar.WEEK_OF_YEAR;
					amountIntervallo = rst.getOgniSettimane().intValue();
				} else if (rst.getOgniMinuti() != null) {
					intervallo = rst.getOgniMinuti().toString()
							+ (new Long(1).equals(rst.getOgniMinuti()) ? " minuto"
									: " minuti");
					campoIntervallo = Calendar.MINUTE;
					amountIntervallo = rst.getOgniMinuti().intValue();
				}
				dto.setIntervallo(intervallo);

				dto.setPeriodica("".equals(intervallo) ? false : true);

				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				/*
				 * RCommandLaunch cLaunch = recuperaComandoService
				 * .getLastRCommandLaunch(rst.getRCommand().getId());
				 */
				if (/* cLaunch != null && */dto.isPeriodica()) {
					/*
					 * Date start = cLaunch.getDateStart(); Calendar c =
					 * Calendar.getInstance(); c.setTime(start);
					 * c.add(campoIntervallo, amountIntervallo);
					 * dto.setDataProxEx(sdf.format(c.getTime()));
					 */
					Calendar c = quartzService
							.getNextExecutionTime(rst);
					if(c == null){
						//il job non è presente in quartz, lo sincronizzo
						sincronizzaSchedulerTime(rst);
						c = quartzService
								.getNextExecutionTime(rst);
						isSincronized = false;
					}
					dto.setDataProxEx(sdf.format((c).getTime()));

				} else
					dto.setDataProxEx(sdf.format(rst.getDtStart()));

				listaSchedulerDTOAuto.add(dto);
			}

			if (!"closed".equals(statolistaSchedulerDTOScaduti))
				doCaricaListaSchedulerTimeScaduti();
			
			if(!isSincronized){
				super.addInfoMessage("scheduler.synchronize");
			}

		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
			super.addErrorMessage("scheduler.lista.error", e.getMessage());
		}

	}

	public void doCaricaListaSchedulerTimeScaduti() {

		listaSchedulerDTOScaduti = new ArrayList<SchedulerDTO>();

		logger.debug("Caricamento lista scheduler time scaduti");

		try {

			List<RSchedulerTime> listaRTime = new ArrayList<RSchedulerTime>();
			if (enteSelezionato == null || "".equals(enteSelezionato))
				listaRTime = schedulerTimeService
						.getSchedulerProcessesExpired(listaEntiAuth);
			else
				listaRTime = schedulerTimeService
						.getSchedulerProcessesExpired(enteSelezionato);

			for (RSchedulerTime rst : listaRTime) {

				SchedulerDTO dto = new SchedulerDTO();
				dto.setrScheduler(rst);
				dto.setEnte(comuneService.getComune(rst.getBelfiore())
						.getDescrizione());

				List<RFontedatiCommand> fontiDati = recuperaComandoService
						.getRCommandFDs(rst.getRCommand());
				String stringaFonteDati = "";
				if (fontiDati.size() > 0) {
					for (RFontedatiCommand fontedatiCommand : fontiDati) {

						AmFonteComune fc = comuneService
								.getFonteComuneByComuneFonte(rst.getBelfiore(),
										fontedatiCommand.getId().getIdFonte()
												.intValue());
						if (fc != null) {
							stringaFonteDati += stringaFonteDati.equals("") ? fc
									.getAmFonte().getDescrizione()
									: "; " + fc.getAmFonte().getDescrizione();
						}

					}
				}
				dto.setFonte(stringaFonteDati);

				String intervallo = "";
				int campoIntervallo = 0;
				int amountIntervallo = 0;
				if (rst.getOgniGiorni() != null) {
					intervallo = rst.getOgniGiorni().toString()
							+ (new Long(1).equals(rst.getOgniGiorni()) ? " giorno"
									: " giorni");
					campoIntervallo = Calendar.DAY_OF_YEAR;
					amountIntervallo = rst.getOgniGiorni().intValue();
				} else if (rst.getOgniMesi() != null) {
					intervallo = rst.getOgniMesi().toString()
							+ (new Long(1).equals(rst.getOgniMesi()) ? " mese"
									: " mesi");
					campoIntervallo = Calendar.MONTH;
					amountIntervallo = rst.getOgniMesi().intValue();
				} else if (rst.getOgniOre() != null) {
					intervallo = rst.getOgniOre().toString()
							+ (new Long(1).equals(rst.getOgniOre()) ? " ora"
									: " ore");
					campoIntervallo = Calendar.HOUR;
					amountIntervallo = rst.getOgniOre().intValue();
				} else if (rst.getOgniSettimane() != null) {
					intervallo = rst.getOgniSettimane().toString()
							+ (new Long(1).equals(rst.getOgniSettimane()) ? " settimana"
									: " settimane");
					campoIntervallo = Calendar.WEEK_OF_YEAR;
					amountIntervallo = rst.getOgniSettimane().intValue();
				} else if (rst.getOgniMinuti() != null) {
					intervallo = rst.getOgniMinuti().toString()
							+ (new Long(1).equals(rst.getOgniMinuti()) ? " minuto"
									: " minuti");
					campoIntervallo = Calendar.MINUTE;
					amountIntervallo = rst.getOgniMinuti().intValue();
				}
				dto.setIntervallo(intervallo);

				dto.setPeriodica("".equals(intervallo) ? false : true);

				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				RCommandLaunch cLaunch = recuperaComandoService
						.getLastRCommandLaunch(rst.getRCommand().getId(),
								enteSelezionato);
				if (cLaunch != null && dto.isPeriodica()) {
					Date start = cLaunch.getDateStart();
					Calendar c = Calendar.getInstance();
					c.setTime(start);
					c.add(campoIntervallo, amountIntervallo);
					dto.setDataProxEx(sdf.format(c.getTime()));
				} else
					dto.setDataProxEx(sdf.format(rst.getDtStart()));

				listaSchedulerDTOScaduti.add(dto);
			}
		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
			super.addErrorMessage("scheduler.lista.error", e.getMessage());
		}

	}

	public void doCaricaLog() {

		listaLog = new ArrayList<LogDTO>();

		try {

			List<RCommandLaunch> rcl = (List<RCommandLaunch>) recuperaComandoService
					.getRCommandLaunchByScheduler(rSchedulerTimeId);

			int cnt = rcl.size();
			if (cnt > 0) {
				for (RCommandLaunch cl : rcl) {
					LogDTO dto = new LogDTO();
					dto.setCommandLaunch(cl);

					RTracciaStati tracciaStato = tracciaStatiService
							.getTracciaStatoByProcessId(cl.getProcessid());
					if (tracciaStato != null)
						dto.setRAnagStato(tracciaStato.getRAnagStati());
					if (dto.getRAnagStato() == null) {
						RProcessMonitor processMonitor = processMonitorService
								.getProcessMonitorByProcessId(cl.getProcessid());
						if (processMonitor != null)
							dto.setRAnagStato(processMonitor.getRAnagStato());
					}
					if (dto.getRAnagStato() != null) {
						switch (dto.getRAnagStato().getId().intValue()) {
						case 7:
						case 8:
						case 9:
						case 10: {
							dto.setProcessing(true);
						}
						default: {
						}
						}
					}

					dto.setCommandAck(recuperaComandoService.getRCommandAck(cl
							.getId()));
					listaLog.add(dto);
				}
			}

		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
		}

	}

	private void sincronizzaSchedulerTime(RSchedulerTime rst) {
		logger.info("Sincronizzazione scheduler time");
		String msg = "scheduler.synchronize";

		try {

			quartzService.addJob(rst);
			
		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
			super.addErrorMessage(msg + ".error", e.getMessage());
		}

	}

	public void doDeleteSchedulerTime() {
		logger.info("Eliminazione scheduler time");
		String msg = "scheduler.delete";

		try {
			// debug lista prima
			List<String> listaJobs = quartzService.getJobs();
			logger.debug("____lista Jobs prima della cancellazione___");
			for (String job : listaJobs)
				logger.debug(job);

			try {
				RSchedulerTime rst = schedulerTimeService
						.getScheduledProcessesById(rSchedulerTimeId);
				quartzService.deleteJob(rst);
			} catch (Exception e) {
				logger.warn("Eccezione: " + e.getMessage(), e);
			}
			schedulerTimeService
					.deleteSchedulerProcessInstanceById(rSchedulerTimeId);

			// debug lista dopo
			listaJobs = quartzService.getJobs();
			logger.debug("____lista Jobs dopo della cancellazione___");
			for (String job : listaJobs)
				logger.debug(job);
			super.addInfoMessage(msg);

			// update
			doCaricaListaSchedulerTime();
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}

	}

	public void doDeleteSchedulerTimeExpired() {
		logger.info("Eliminazione scheduler time scaduto");
		String msg = "scheduler.delete";

		try {

			schedulerTimeService
					.deleteSchedulerProcessInstanceById(rSchedulerTimeId);

			super.addInfoMessage(msg);

			// update
			doCaricaListaSchedulerTimeScaduti();
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}

	}

	public void doDeleteAllExpired() {

		logger.info("Eliminazione scheduler time scaduti");
		String msg = "scheduler.delete";

		try {

			schedulerTimeService.deleteSchedulerProcessInstanceExpired();

			// update
			doCaricaListaSchedulerTimeScaduti();
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}

	}

	public void resetPage() {

		schedulerLogPage = "/jsp/protected/empty.xhtml";

	}

	public List<SchedulerDTO> getListaSchedulerDTOAuto() {
		return listaSchedulerDTOAuto;
	}

	public void setListaSchedulerDTOAuto(
			List<SchedulerDTO> listaSchedulerDTOAuto) {
		this.listaSchedulerDTOAuto = listaSchedulerDTOAuto;
	}

	public List<SchedulerDTO> getListaSchedulerDTOManual() {
		return listaSchedulerDTOManual;
	}

	public void setListaSchedulerDTOManual(
			List<SchedulerDTO> listaSchedulerDTOManual) {
		this.listaSchedulerDTOManual = listaSchedulerDTOManual;
	}

	public String getEnteSelezionato() {
		return enteSelezionato;
	}

	public void setEnteSelezionato(String enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}

	public Long getrSchedulerTimeId() {
		return rSchedulerTimeId;
	}

	public void setrSchedulerTimeId(Long rSchedulerTimeId) {
		this.rSchedulerTimeId = rSchedulerTimeId;
	}

	public List<SchedulerDTO> getListaSchedulerDTOScaduti() {
		return listaSchedulerDTOScaduti;
	}

	public void setListaSchedulerDTOScaduti(
			List<SchedulerDTO> listaSchedulerDTOScaduti) {
		this.listaSchedulerDTOScaduti = listaSchedulerDTOScaduti;
	}

	public String getStatolistaSchedulerDTOScaduti() {
		return statolistaSchedulerDTOScaduti;
	}

	public void setStatolistaSchedulerDTOScaduti(
			String statolistaSchedulerDTOScaduti) {
		this.statolistaSchedulerDTOScaduti = statolistaSchedulerDTOScaduti;
	}

	public List<SelectItem> getListaEnti() {
		return listaEnti;
	}

	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}

	public List<LogDTO> getListaLog() {
		return listaLog;
	}

	public void setListaLog(List<LogDTO> listaLog) {
		this.listaLog = listaLog;
	}

	public String getSchedulerLogPage() {
		return schedulerLogPage;
	}

	public void setSchedulerLogPage(String schedulerLogPage) {
		this.schedulerLogPage = schedulerLogPage;
	}

}
