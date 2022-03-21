package it.webred.ct.rulengine.web.bean.monitor;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RCommandType;
import it.webred.ct.rulengine.dto.MonitorDTO;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.web.bean.util.UploadBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

public class PMonitorBean extends MonitorBaseBean implements Serializable {

	private static Logger logger = Logger.getLogger(PMonitorBean.class
			.getName());

	private String enteSelezionato;
	private String fonteDatiSelezionata;
	private String tipologiaSelezionata;

	private List<SelectItem> listaEnti = new ArrayList<SelectItem>();
	private List<MonitorDTO> listaMonitorI = new ArrayList<MonitorDTO>();
	private List<MonitorDTO> listaMonitorE = new ArrayList<MonitorDTO>();

	public String goMonitor() {

		if (listaEnti.size() == 0) {
			logger.debug("Inizializzazione Monitor");
			List<AmComune> listaComuni = getListaEntiAuth();
			for (AmComune comune : listaComuni) {
				listaEnti.add(new SelectItem(comune.getBelfiore(), comune
						.getDescrizione()));
			}
			if (listaEnti.size() > 0) {
				enteSelezionato = (String) listaEnti.get(0).getValue();
				doSearchFontiDatiEnte();
			}
		}
		return "controller.monitor";
	}

	public void doSearchFontiDatiEnte() {

		logger.info("Recupero fonti dati legate all'ente");
		logger.info("Ente: " + enteSelezionato);
		listaMonitorI = new ArrayList<MonitorDTO>();
		listaMonitorE = new ArrayList<MonitorDTO>();

		try {

			List<AmFonteComune> listaFonti = comuneService
					.getListaFonteByComune(enteSelezionato);

			List<SelectItem> listaFontiSelect = new ArrayList<SelectItem>();
			HashMap<Long, RCommand> listaCommandRunning = new HashMap<Long, RCommand>();
			for (AmFonteComune fonte : listaFonti) {

				MonitorDTO dto = new MonitorDTO();
				dto.setFonte(fonte.getAmFonte());
				Task t = new Task();
				t.setBelfiore(enteSelezionato);
				t.setIdFonte(new Long(fonte.getAmFonte().getId()));
				// verifica per reperimento
				t.setDescription("Reperimento status for Fonte: "
						+ fonte.getAmFonte().getDescrizione());
				t.setIdTipologia(new Long(10));
				// recupero comando
				RCommand rCommand = recuperaComandoService.getRCommand(t);
				if (rCommand != null && rCommand.getSystemCommand().intValue() != 3) {
					dto.setRepCommandId(rCommand.getId());
					t.setFreeObj(rCommand);
					Task newT = verificaService.verificaInizialeProcesso(t);
					dto.setRepProcessable(newT.isProcessable());
					String status = newT.getStatus();
					logger.debug("Status R: "+status);
					if (status != null && !"".equals(status)) {
						int ind = status.indexOf("[");
						dto.setRepStatus(status.substring(ind + 1, ind + 2));
						dto.setRepDescription(status.substring(0, ind));
					}
					if (dto.getRepDescription() == null	|| "".equals(dto.getRepDescription()))
						dto.setRepDescription("Log");
				}
				// verifica per acquisizione
				t.setDescription("Acquisizione status for Fonte: "
						+ fonte.getAmFonte().getDescrizione());
				t.setIdTipologia(new Long(20));
				// recupero comando
				rCommand = recuperaComandoService.getRCommand(t);
				if (rCommand != null && rCommand.getSystemCommand().intValue() != 3) {
					dto.setAcqCommandId(rCommand.getId());
					t.setFreeObj(rCommand);
					Task newT = verificaService.verificaInizialeProcesso(t);
					dto.setAcqProcessable(newT.isProcessable());
					String status = newT.getStatus();
					logger.debug("Status A: "+status);
					if (status != null && !"".equals(status)) {
						int ind = status.indexOf("[");
						dto.setAcqStatus(status.substring(ind + 1, ind + 2));
						dto.setAcqDescription(status.substring(0, ind));
					}
					if (dto.getAcqDescription() == null
							|| "".equals(dto.getAcqDescription()))
						dto.setAcqDescription("Log");
				}
				// verifica per trattamento
				t.setDescription("Trattamento status for Fonte: "
						+ fonte.getAmFonte().getDescrizione());
				Task newT = processMonitorService.isTrattamentoRunning(t);
				dto.setTrtProcessable(newT.isProcessable());

				if (!dto.isTrtProcessable()) {
					RCommand rc = recuperaComandoService
							.getRCommand((String) newT.getFreeObj());
					if (rc != null)
						dto.setTrtCommandId(rc.getId());
					listaCommandRunning.put(rc.getId(), rc);
				}

				dto.setTrtStatus(newT.getStatus());

				if ("I".equals(fonte.getAmFonte().getTipoFonte()))
					listaMonitorI.add(dto);
				else
					listaMonitorE.add(dto);

				listaFontiSelect.add(new SelectItem(fonte.getId()
						.getFkAmFonte(), fonte.getAmFonte().getDescrizione()));

			}

			// caricamento command monitor
			CMonitorBean cBean = (CMonitorBean) getBeanReference("cMonitorBean");
			cBean.setEnteSelezionato(enteSelezionato);
			cBean.setListaFonti(listaFontiSelect);
			cBean.setListaCommandRunning(listaCommandRunning);
			if (!enteSelezionato.equals(""))
				cBean.doCaricaListaRCommand();
			else
				cBean.setListaCommand(new ArrayList());

		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
		}

		logger.debug("Fonti recuperate");

	}

	public void submit() {

		logger.info("Preparazione chiamata EJB");

		if (enteSelezionato.equals("")) {
			logger.warn("Nessun ente specificato");
			super.addErrorMessage("noente.error", "");
		} else {
			logger.info("Inizio " + fonteDatiSelezionata + " ["
					+ enteSelezionato + "]");
			long start = System.currentTimeMillis();
			logger.info("start time: " + start);
			String msg = "schedule.task";

			try {
				Task t = new Task();
				t.setBelfiore(enteSelezionato);
				t.setIdFonte(new Long(fonteDatiSelezionata));
				t.setIdTipologia(new Long(tipologiaSelezionata));
				t.setDescription("Invio schedule con belfiore: "
						+ t.getBelfiore() + ", fonte id:" + t.getIdFonte()
						+ " e id tipologia:" + t.getIdTipologia());
				// recupero comando
				RCommand rCommand = recuperaComandoService.getRCommand(t);
				t.setFreeObj(rCommand);

				logger.info("Servizio EJB (invoke): " + processService);
				processService.scheduleTask(t);

				//refresh
				doSearchFontiDatiEnte();
				CMonitorBean cBean = (CMonitorBean) getBeanReference("cMonitorBean");
				cBean.doCaricaListaRCommand();
				
				super.addInfoMessage(msg);
			} catch (Exception e) {
				logger.error("Eccezione: " + e.getMessage(), e);
				super.addErrorMessage(msg + ".error", e.getMessage());
			} finally {
				long end = System.currentTimeMillis();
				logger.info("end time: " + end);
			}
		}

	}

	public void doCaricaDirFiles() {

		ParameterSearchCriteria sCriteria = new ParameterSearchCriteria();
		sCriteria.setFonte(fonteDatiSelezionata);
		sCriteria.setComune(enteSelezionato);
		sCriteria.setKey(super.getParamValue("path_dir_files"));
		AmKeyValueExt parametroDirFiles = parameterService
				.getAmKeyValueExt(sCriteria);
		UploadBean uplBean = (UploadBean) getBeanReference("uploadBean");
		uplBean.setParameterPath(parametroDirFiles.getValueConf());
		uplBean.initPanel();
	}

	public String getEnteSelezionato() {
		return enteSelezionato;
	}

	public void setEnteSelezionato(String enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}

	public String getFonteDatiSelezionata() {
		return fonteDatiSelezionata;
	}

	public void setFonteDatiSelezionata(String fonteDatiSelezionata) {
		this.fonteDatiSelezionata = fonteDatiSelezionata;
	}

	public List<SelectItem> getListaEnti() {
		return listaEnti;
	}

	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}

	public List<MonitorDTO> getListaMonitorI() {
		return listaMonitorI;
	}

	public void setListaMonitorI(List<MonitorDTO> listaMonitorI) {
		this.listaMonitorI = listaMonitorI;
	}

	public List<MonitorDTO> getListaMonitorE() {
		return listaMonitorE;
	}

	public void setListaMonitorE(List<MonitorDTO> listaMonitorE) {
		this.listaMonitorE = listaMonitorE;
	}

	public String getTipologiaSelezionata() {
		return tipologiaSelezionata;
	}

	public void setTipologiaSelezionata(String tipologiaSelezionata) {
		this.tipologiaSelezionata = tipologiaSelezionata;
	}

}
