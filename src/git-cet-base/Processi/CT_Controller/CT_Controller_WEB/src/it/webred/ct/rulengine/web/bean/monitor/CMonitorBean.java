package it.webred.ct.rulengine.web.bean.monitor;

import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RCommandAck;
import it.webred.ct.rulengine.controller.model.RCommandLaunch;
import it.webred.ct.rulengine.controller.model.RCommandType;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.controller.model.RProcessMonitorPK;
import it.webred.ct.rulengine.controller.model.RTracciaStati;
import it.webred.ct.rulengine.dto.CommandDTO;
import it.webred.ct.rulengine.dto.LogDTO;
import it.webred.ct.rulengine.dto.Task;
import it.webred.utils.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import com.ibm.icu.util.GregorianCalendar;

public class CMonitorBean extends MonitorBaseBean implements Serializable {

	private static Logger logger = Logger.getLogger(CMonitorBean.class
			.getName());

	private String commandTypeSelezionato = "31";
	private String enteSelezionato;
	private String fonteSelezionata;
	private String commandIdSelezionato;
	
	private String meseSelezionato;
	private String annoSelezionato;
	private List<SelectItem> listaMesi;
	private List<SelectItem> listaAnni;

	private List<CommandDTO> listaCommand = new ArrayList<CommandDTO>();
	private HashMap<Long, RCommand> listaCommandRunning = new HashMap<Long, RCommand>();
	private List<SelectItem> listaFonti = new ArrayList<SelectItem>();
	private List<LogDTO> listaLog = new ArrayList<LogDTO>();

	private String commandLogPage = "/jsp/protected/empty.xhtml";

	public void doCaricaListaRCommand() {

		if (listaFonti.size() > 0) {
			List<Long> fonti = new ArrayList<Long>();
			if (fonteSelezionata == null || "".equals(fonteSelezionata)) {
				for (SelectItem fonte : listaFonti) {
					fonti.add(new Long((Integer)fonte.getValue()));
				}
			} else
				fonti.add(new Long(fonteSelezionata));

			List<RCommand> lista = recuperaComandoService
					.getRCommandsByFontiAndType(enteSelezionato, fonti,
							new Long(commandTypeSelezionato));
			
			lista.addAll(recuperaComandoService.getRCommandsByTypeWithoutFonti(enteSelezionato, new Long(commandTypeSelezionato)));

			listaCommand = new ArrayList<CommandDTO>();
			for (RCommand rc : lista) {
				CommandDTO dto = new CommandDTO();
				dto.setrCommand(rc);
				if (listaCommandRunning.get(rc.getId()) != null)
					dto.setRunning(true);

				Task t = new Task();
				t.setBelfiore(enteSelezionato);
				t.setDescription("Reperimento status per comando: "
						+ rc.getCodCommand());
				t.setIdTipologia(new Long(commandTypeSelezionato));
				t.setFreeObj(rc);
				Task newT = verificaService
						.verificaInizialeProcessiTrattamento(t);
				dto.setProcessable(newT.isProcessable());
				String status = newT.getStatus();
				logger.debug("STATUS: " + status);
				if (status != null && !"".equals(status)) {
					int ind = status.indexOf("[");
					dto.setStatus(status.substring(ind + 1, ind + 2));
					dto.setDescription(status.substring(0, ind));
				}
				if (dto.getDescription() == null
						|| "".equals(dto.getDescription()))
					dto.setDescription("Log");

				listaCommand.add(dto);
			}
		}
	}

	public void submit() {

		logger.info("Preparazione chiamata EJB");

		if (enteSelezionato.equals("")) {
			logger.warn("Nessun ente specificato");
			super.addErrorMessage("noente.error", "");
		} else {
			long start = System.currentTimeMillis();
			logger.info("start time: " + start);
			String msg = "schedule.task";

			try {
				Task t = new Task();
				t.setBelfiore(enteSelezionato);
				t.setIdTipologia(new Long(commandTypeSelezionato));
				t.setDescription("Invio schedule con belfiore: "
						+ t.getBelfiore() + " e id tipologia:"
						+ t.getIdTipologia());
				// recupero comando
				RCommand rCommand = recuperaComandoService
						.getRCommand(new Long(commandIdSelezionato));
				t.setFreeObj(rCommand);
				
				//va recuperata anche la o le fd legate al cmd
				List<RFontedatiCommand> fdc = recuperaComandoService.getRCommandFDs(rCommand);
				if(fdc.size() == 1)
					t.setIdFonte(fdc.get(0).getId().getIdFonte()); //1 fd coinvolta				
				
				logger.info("Servizio EJB (invoke): " + processService);
				processService.scheduleTask(t);

				// refresh
				doCaricaListaRCommand();
				PMonitorBean pBean = (PMonitorBean) getBeanReference("pMonitorBean");
				pBean.doSearchFontiDatiEnte();

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
	
	public void doCaricaLog() {

		if (commandIdSelezionato != null && !"".equals(commandIdSelezionato)) {

			listaLog = new ArrayList<LogDTO>();
			long myId = -1;
			LogDTO dto = null;

			try {
				List<Object[]> dati = recuperaComandoService.getRCommandLaunchJoinStatiAck(new Long(commandIdSelezionato), enteSelezionato, meseSelezionato, annoSelezionato);
				if (dati != null && dati.size() > 0) {
					for (Object[] riga : dati) {
						long id = new Long(riga[0].toString()).longValue();
						if (myId != id) {
							if (dto != null) {
								listaLog.add(dto);
							}
							dto = new LogDTO();
							RCommandLaunch cl = new RCommandLaunch();
							cl.setId(new Long(id));
							cl.setFkCommand(riga[1] == null ? null : new Long(riga[1].toString()));
							cl.setDateStart((Date)riga[2]);
							cl.setDateEnd((Date)riga[3]);
							cl.setProcessid(riga[4].toString());
							cl.setBelfiore(riga[5] == null ? null : riga[5].toString());
							cl.setIdSched(riga[6] == null ? null : new Long(riga[6].toString()));
							dto.setCommandLaunch(cl);
							
							if (riga[7] != null) {
								RAnagStati ras = new RAnagStati();
								ras.setId(new Long(riga[8].toString()));
								ras.setDescr(riga[9].toString());
								RCommandType rct = new RCommandType();
								rct.setId(new Long(riga[11].toString()));
								rct.setDescr(riga[12] == null ? null : riga[12].toString());
								ras.setRCommandType(rct);
								ras.setTipo(riga[10] == null ? null : riga[10].toString());
								dto.setRAnagStato(ras);
							}
								
							if (dto.getRAnagStato() == null) {
								RProcessMonitor processMonitor = processMonitorService.getProcessMonitorByProcessId(cl.getProcessid());
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
							
							List<RCommandAck> acks = new ArrayList<RCommandAck>();
							RCommandAck ack = getRCommandAckForLog(riga);
							acks.add(ack);
							dto.setCommandAck(acks);
							
							myId = id;
						} else {
							RCommandAck ack = getRCommandAckForLog(riga);
							dto.getCommandAck().add(ack);
						}
					}
					
					//ultimo elemento
					if (dto != null) {
						listaLog.add(dto);
					}
				}				

			} catch (Exception e) {
				logger.error("Eccezione: " + e.getMessage(), e);
			}

		}

	}
	
	protected RCommandAck getRCommandAckForLog(Object[] riga) {
		RCommandAck ack = new RCommandAck();
		ack.setId(new Long(riga[13].toString()));
		ack.setAckName(riga[17] == null ? null : riga[17].toString());
		ack.setFkCommandLaunch(riga[15] == null ? null : new Long(riga[15].toString()));
		ack.setLogDate((Date)riga[16]);
		ack.setMessage(riga[14] == null ? null : riga[14].toString());
		RCommand rc = new RCommand();
		rc.setId(new Long(riga[18].toString()));
		rc.setCodCommand(riga[22].toString());
		rc.setDescr(riga[19] == null ? null : riga[19].toString());
		rc.setLongDescr(riga[21] == null ? null : riga[21].toString());
		rc.setSystemCommand(new Long(riga[20].toString()));
		RCommandType rct1 = new RCommandType();
		rct1.setId(new Long(riga[23].toString()));
		rct1.setDescr(riga[24] == null ? null : riga[24].toString());
		rc.setRCommandType(rct1);
		ack.setRCommand(rc);
		return ack;
	}

	//metodo non più utilizzato, sostituito dall'attuale doCaricaLog()
	public void doCaricaLogOLD() {

		if (commandIdSelezionato != null || !"".equals(commandIdSelezionato)) {

			listaLog = new ArrayList<LogDTO>();

			try {

				List<RCommandLaunch> rcl = (List<RCommandLaunch>) recuperaComandoService
						.getRCommandLaunch(new Long(commandIdSelezionato),
								enteSelezionato);

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
									.getProcessMonitorByProcessId(cl
											.getProcessid());
							if (processMonitor != null)
								dto.setRAnagStato(processMonitor
										.getRAnagStato());
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

						dto.setCommandAck(recuperaComandoService
								.getRCommandAck(cl.getId()));
						listaLog.add(dto);
					}
				}

			} catch (Exception e) {
				logger.error("Eccezione: " + e.getMessage(), e);
			}

		}

	}

	public void resetPage() {

		commandLogPage = "/jsp/protected/empty.xhtml";

	}

	public String getCommandTypeSelezionato() {
		return commandTypeSelezionato;
	}

	public void setCommandTypeSelezionato(String commandTypeSelezionato) {
		this.commandTypeSelezionato = commandTypeSelezionato;
	}

	public List<CommandDTO> getListaCommand() {
		return listaCommand;
	}

	public void setListaCommand(List<CommandDTO> listaCommand) {
		this.listaCommand = listaCommand;
	}

	public String getEnteSelezionato() {
		return enteSelezionato;
	}

	public void setEnteSelezionato(String enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}

	public String getFonteSelezionata() {
		return fonteSelezionata;
	}

	public void setFonteSelezionata(String fonteSelezionata) {
		this.fonteSelezionata = fonteSelezionata;
	}

	public List<SelectItem> getListaFonti() {
		return listaFonti;
	}

	public void setListaFonti(List<SelectItem> listaFonti) {
		this.listaFonti = listaFonti;
	}

	public List<LogDTO> getListaLog() {
		return listaLog;
	}

	public void setListaLog(List<LogDTO> listaLog) {
		this.listaLog = listaLog;
	}

	public String getCommandIdSelezionato() {
		return commandIdSelezionato;
	}

	public void setCommandIdSelezionato(String commandIdSelezionato) {
		this.commandIdSelezionato = commandIdSelezionato;
	}	

	public String getMeseSelezionato() {
		return meseSelezionato;
	}

	public void setMeseSelezionato(String meseSelezionato) {
		this.meseSelezionato = meseSelezionato;
	}

	public String getAnnoSelezionato() {
		return annoSelezionato;
	}

	public void setAnnoSelezionato(String annoSelezionato) {
		this.annoSelezionato = annoSelezionato;
	}

	public List<SelectItem> getListaMesi() {
		listaMesi = new ArrayList<SelectItem>();
		listaMesi.add(new SelectItem("01", "gennaio"));
		listaMesi.add(new SelectItem("02", "febbraio"));
		listaMesi.add(new SelectItem("03", "marzo"));
		listaMesi.add(new SelectItem("04", "aprile"));
		listaMesi.add(new SelectItem("05", "maggio"));
		listaMesi.add(new SelectItem("06", "giugno"));
		listaMesi.add(new SelectItem("07", "luglio"));
		listaMesi.add(new SelectItem("08", "agosto"));
		listaMesi.add(new SelectItem("09", "settembre"));
		listaMesi.add(new SelectItem("10", "ottobre"));
		listaMesi.add(new SelectItem("11", "novembre"));
		listaMesi.add(new SelectItem("12", "dicembre"));
		return listaMesi;
	}

	public List<SelectItem> getListaAnni() {
		listaAnni = new ArrayList<SelectItem>();
		for (int i = new Integer(getAnnoCorr()).intValue(); i >= 2000; i--) {
			listaAnni.add(new SelectItem("" + i, "" + i));
		}
		return listaAnni;
	}

	public String getCommandLogPage() {
		return commandLogPage;
	}

	public void setCommandLogPage(String commandLogPage) {
		this.commandLogPage = commandLogPage;
	}

	public HashMap<Long, RCommand> getListaCommandRunning() {
		return listaCommandRunning;
	}

	public void setListaCommandRunning(
			HashMap<Long, RCommand> listaCommandRunning) {
		this.listaCommandRunning = listaCommandRunning;
	}
	
	public String getAnnoCorr() {
		return "" + new GregorianCalendar().get(Calendar.YEAR);
	}
	
	public String getMeseCorr() {
		String meseCorr = "" + (new GregorianCalendar().get(Calendar.MONTH) + 1);
		return StringUtils.padding(meseCorr, 2, '0', true);
	}

}
