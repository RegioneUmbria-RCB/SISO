package it.webred.ct.rulengine.web.bean.scheduler;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RCommandType;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.controller.model.RSchedulerTime;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;



import org.apache.log4j.Logger;

public class SNewBean extends SchedulerBaseBean implements Serializable {

	private static Logger logger = Logger.getLogger(SNewBean.class.getName());

	private List<SelectItem> listaEnti = new ArrayList<SelectItem>();
	private List<SelectItem> listaFonti = new ArrayList<SelectItem>();
	private List<SelectItem> listaTipoOp = new ArrayList<SelectItem>();
	private List<SelectItem> listaComandi = new ArrayList<SelectItem>();
	private List<SelectItem> listaValoreIntervallo = new ArrayList<SelectItem>();
	private List<SelectItem> listaTipoIntervallo = new ArrayList<SelectItem>();

	private Long rSchedulerTimeId;
	private String ente;
	private String tipoOp;
	private String fonte;
	private String comando;
	private boolean intervallo;
	private String valoreIntervallo;
	private String tipoIntervallo;
	private String tipoEsecuzione;
	private Date dataInizio;
	private Date dataFine;

	private String newAttPage = "/jsp/protected/empty.xhtml";

	public void doInitNewScheduler() {
		logger.debug("Inizializzazione");

		doCaricaEnti();
		if (listaEnti.size() > 0)
			ente = listaEnti.get(0).getValue().toString();

		doCaricaTipoOp();
		if (listaTipoOp.size() > 0)
			tipoOp = ((Long) listaTipoOp.get(0).getValue()).toString();

		if (ente != null)
			doCaricaFonti();
		if (listaFonti.size() > 0)
			fonte = listaFonti.get(0).getValue().toString();

		doCaricaComandi();
		if (listaComandi.size() > 0)
			comando = listaComandi.get(0).getValue().toString();
		doCaricaTipoIntervallo();
		if (listaTipoIntervallo.size() > 0)
			tipoIntervallo = listaTipoIntervallo.get(0).getValue().toString();
		doCaricaValoreIntervallo();
		if (listaValoreIntervallo.size() > 0)
			valoreIntervallo = listaValoreIntervallo.get(0).getValue().toString();

		intervallo = false;
		tipoEsecuzione = "A";
		dataInizio = new Date();
		dataFine = null;
	}

	public void doCaricaSchedulerTime() {
		logger.info("Caricamento dati scheduler time");
		if (rSchedulerTimeId != null) {

			doInitNewScheduler();

			RSchedulerTime rst = schedulerTimeService
					.getScheduledProcessesById(rSchedulerTimeId);
			ente = rst.getBelfiore();
			tipoOp = rst.getRCommand().getRCommandType().getId().toString();

			doCaricaFonti();
			List<RFontedatiCommand> fontiDati = recuperaComandoService
					.getRCommandFDs(rst.getRCommand());
			if (fontiDati.size() == 1) {
				fonte = fontiDati.get(0).getId().getIdFonte().toString();
			}

			doCaricaComandi();
			comando = rst.getRCommand().getId().toString();
			intervallo = false;
			if (rst.getOgniMinuti() != null) {
				tipoIntervallo = "m";
				valoreIntervallo = rst.getOgniMinuti().toString();
				intervallo = true;
			}
			if (rst.getOgniOre() != null) {
				tipoIntervallo = "O";
				valoreIntervallo = rst.getOgniOre().toString();
				intervallo = true;
			}
			if (rst.getOgniGiorni() != null) {
				tipoIntervallo = "G";
				valoreIntervallo = rst.getOgniGiorni().toString();
				intervallo = true;
			}
			if (rst.getOgniSettimane() != null) {
				tipoIntervallo = "S";
				valoreIntervallo = rst.getOgniSettimane().toString();
				intervallo = true;
			}
			if (rst.getOgniMesi() != null) {
				tipoIntervallo = "M";
				valoreIntervallo = rst.getOgniMesi().toString();
				intervallo = true;
			}
			tipoEsecuzione = "A";
			dataInizio = rst.getDtStart();
			dataFine = rst.getDtEnd();

		}
	}

	public void doCaricaEnti() {
		logger.info("Recupero enti legate all'utente");

		if (listaEnti.size() == 0) {
			try {
				List<AmComune> listaComuni = getListaEntiAuth();
				for (AmComune comune : listaComuni) {
					listaEnti.add(new SelectItem(comune.getBelfiore(), comune
							.getDescrizione()));
				}
			} catch (Exception e) {
				logger.error("Eccezione: " + e.getMessage(), e);
			}
		}

	}

	public void doCaricaFonti() {
		logger.info("Recupero fonti dati legate all'ente");

		try {
			List<AmFonteComune> lista = comuneService
					.getListaFonteByComune(ente);
			listaFonti = new ArrayList<SelectItem>();

			for (AmFonteComune fc : lista) {
				SelectItem item = new SelectItem(fc.getId().getFkAmFonte(), fc
						.getAmFonte().getDescrizione());
				listaFonti.add(item);
			}

			// carico comandi
			if (listaFonti.size() > 0) {
				fonte = (String) listaFonti.get(0).getValue();
				doCaricaComandi();
				if (listaComandi.size() > 0)
					comando = listaComandi.get(0).getValue().toString();
			}

		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
		}
	}

	public void doCaricaTipoOp() {
		logger.info("Recupero tipo operazioni");

		if (listaTipoOp.size() == 0) {
			try {
				List<RCommandType> lista = mainControllerService
						.getListRCommandType();
				listaTipoOp = new ArrayList<SelectItem>();

				for (RCommandType ct : lista) {
					SelectItem item = new SelectItem(ct.getId(), ct.getDescr());
					listaTipoOp.add(item);
				}
			} catch (Exception e) {
				logger.error("Eccezione: " + e.getMessage(), e);
			}
		}
	}

	public void doCaricaTipoIntervallo() {

		if (listaTipoIntervallo.size() == 0) {
			listaTipoIntervallo = new ArrayList<SelectItem>();

			SelectItem item = new SelectItem("m", "Minuti");
			listaTipoIntervallo.add(item);
			
			item = new SelectItem("O", "Ore");
			listaTipoIntervallo.add(item);

			item = new SelectItem("G", "Giorni");
			listaTipoIntervallo.add(item);

			item = new SelectItem("S", "Settimane");
			listaTipoIntervallo.add(item);

			item = new SelectItem("M", "Mesi");
			listaTipoIntervallo.add(item);
		}
	}

	public void doCaricaValoreIntervallo() {

		if (listaValoreIntervallo.size() == 0) {
			listaValoreIntervallo = new ArrayList<SelectItem>();

			for (int i = 1; i < 91; i++) {
				SelectItem item = new SelectItem(String.valueOf(i));
				listaValoreIntervallo.add(item);
			}
		}
	}

	public void doCaricaComandi() {
		logger.info("Recupero comandi legate ad ente, fonte, tipo operazione");

		if (ente != null && fonte != null && tipoOp != null) {
			listaComandi = new ArrayList<SelectItem>();

			try {
				// recupero comandi
				List<Long> listaFonti = new ArrayList<Long>();
				listaFonti.add(new Long(fonte));
				List<RCommand> rCommandList = recuperaComandoService.getRCommandsByFontiAndType(ente, listaFonti, new Long(tipoOp));

				rCommandList.addAll(recuperaComandoService.getRCommandsByTypeWithoutFonti(ente, new Long(tipoOp)));
				
				for(RCommand rCommand: rCommandList) {
					//tutti i comandi non dummy
					if(rCommand.getSystemCommand().intValue() != 3) {
						SelectItem item = new SelectItem(rCommand.getId(), rCommand.getDescr());
						listaComandi.add(item);
					}
				}
			} catch (Exception e) {
				logger.error("Eccezione: " + e.getMessage(), e);
			}
		}
	}

	public void doSalvaSchedulerTime() {
		logger.info("Salvataggio/Modifica scheduler time");
		String msg = "scheduler.salva";

		try {

			String operazione = "dell'inserimento";
			RSchedulerTime rst = new RSchedulerTime();
			if (!new Long(0).equals(rSchedulerTimeId)) {
				operazione = "della modifica";
				rst = schedulerTimeService
						.getScheduledProcessesById(rSchedulerTimeId);
				rst.setOgniMinuti(null);
				rst.setOgniGiorni(null);
				rst.setOgniMesi(null);
				rst.setOgniOre(null);
				rst.setOgniSettimane(null);
			}
			
			Calendar c = Calendar.getInstance();
			int currentsecond = c.get(Calendar.SECOND);
			c.setTime(dataInizio);
			c.set(Calendar.SECOND, currentsecond);
			rst.setDtStart(c.getTime());
			if (dataFine != null)
				rst.setDtEnd(dataFine);
			else {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				if (intervallo)
					rst.setDtEnd(sdf.parse("31/12/2099 23:59:59"));
				else{
					c.add(Calendar.MINUTE, 1);
					rst.setDtEnd(c.getTime());
				}
					
			}
			if (intervallo && "m".equals(tipoIntervallo))
				rst.setOgniMinuti(new Long(valoreIntervallo));
			if (intervallo && "O".equals(tipoIntervallo))
				rst.setOgniOre(new Long(valoreIntervallo));
			if (intervallo && "G".equals(tipoIntervallo))
				rst.setOgniGiorni(new Long(valoreIntervallo));
			if (intervallo && "S".equals(tipoIntervallo))
				rst.setOgniSettimane(new Long(valoreIntervallo));
			if (intervallo && "M".equals(tipoIntervallo))
				rst.setOgniMesi(new Long(valoreIntervallo));

			RCommand rCommand = recuperaComandoService.getRCommand(new Long(
					comando));
			rst.setJobnameRef("REJob:" + ente + "@" + rCommand.getCodCommand()
					+ "@" + rst.getDtStart().getTime());

			// debug lista prima
			List<String> listaJobs = quartzService.getJobs();
			logger.debug("____lista Jobs prima " + operazione + "___");
			for (String job : listaJobs)
				logger.debug(job);
			if (new Long(0).equals(rSchedulerTimeId)) {
				// save
				rst.setBelfiore(ente);
				rst.setRCommand(rCommand);
				quartzService.addJob(rst);
				schedulerTimeService.saveSchedulerProcessInstance(rst);
			} else {
				// update
				RSchedulerTime rstOld = schedulerTimeService
						.getScheduledProcessesById(rSchedulerTimeId);
				quartzService.editJob(rstOld, rst);
				
				//aggiorno la data fine del vecchio e inserisco uno nuovo
				rstOld.setDtEnd(Calendar.getInstance().getTime());
				schedulerTimeService.saveOrUpdateSchedulerProcessInstance(rstOld);
				
				rst.setId(null);
				schedulerTimeService.saveSchedulerProcessInstance(rst);
			}
			// debug lista dopo
			listaJobs = quartzService.getJobs();
			logger.debug("____lista Jobs dopo " + operazione + "___");
			for (String job : listaJobs)
				logger.debug(job);

			resetPage();
			super.addInfoMessage(msg);
			
			//update
			SMonitorBean sBean = (SMonitorBean) getBeanReference("sMonitorBean");
			sBean.doCaricaListaSchedulerTime();
			
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}

	}

	public void resetPage() {

		newAttPage = "/jsp/protected/empty.xhtml";

	}

	public List<SelectItem> getListaEnti() {
		return listaEnti;
	}

	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}

	public List<SelectItem> getListaFonti() {
		return listaFonti;
	}

	public void setListaFonti(List<SelectItem> listaFonti) {
		this.listaFonti = listaFonti;
	}

	public List<SelectItem> getListaTipoOp() {
		return listaTipoOp;
	}

	public void setListaTipoOp(List<SelectItem> listaTipoOp) {
		this.listaTipoOp = listaTipoOp;
	}

	public List<SelectItem> getListaComandi() {
		return listaComandi;
	}

	public void setListaComandi(List<SelectItem> listaComandi) {
		this.listaComandi = listaComandi;
	}

	public List<SelectItem> getListaValoreIntervallo() {
		return listaValoreIntervallo;
	}

	public void setListaValoreIntervallo(List<SelectItem> listaValoreIntervallo) {
		this.listaValoreIntervallo = listaValoreIntervallo;
	}

	public List<SelectItem> getListaTipoIntervallo() {
		return listaTipoIntervallo;
	}

	public void setListaTipoIntervallo(List<SelectItem> listaTipoIntervallo) {
		this.listaTipoIntervallo = listaTipoIntervallo;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getTipoOp() {
		return tipoOp;
	}

	public void setTipoOp(String tipoOp) {
		this.tipoOp = tipoOp;
	}

	public String getFonte() {
		return fonte;
	}

	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public boolean isIntervallo() {
		return intervallo;
	}

	public void setIntervallo(boolean intervallo) {
		this.intervallo = intervallo;
	}

	public String getValoreIntervallo() {
		return valoreIntervallo;
	}

	public void setValoreIntervallo(String valoreIntervallo) {
		this.valoreIntervallo = valoreIntervallo;
	}

	public String getTipoIntervallo() {
		return tipoIntervallo;
	}

	public void setTipoIntervallo(String tipoIntervallo) {
		this.tipoIntervallo = tipoIntervallo;
	}

	public String getTipoEsecuzione() {
		return tipoEsecuzione;
	}

	public void setTipoEsecuzione(String tipoEsecuzione) {
		this.tipoEsecuzione = tipoEsecuzione;
	}

	public String getNewAttPage() {
		return newAttPage;
	}

	public void setNewAttPage(String newAttPage) {
		this.newAttPage = newAttPage;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public Long getrSchedulerTimeId() {
		return rSchedulerTimeId;
	}

	public void setrSchedulerTimeId(Long rSchedulerTimeId) {
		this.rSchedulerTimeId = rSchedulerTimeId;
	}

}
