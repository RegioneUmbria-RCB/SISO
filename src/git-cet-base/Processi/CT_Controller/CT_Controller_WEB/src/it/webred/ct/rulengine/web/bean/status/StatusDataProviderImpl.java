package it.webred.ct.rulengine.web.bean.status;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.rulengine.controller.model.RCommandLaunch;
import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.controller.model.RTracciaStati;
import it.webred.ct.rulengine.dto.LogDTO;
import it.webred.rulengine.db.RulesConnection;

public class StatusDataProviderImpl extends StatusBaseBean implements
		StatusDataProvider {

	private static Logger logger = Logger.getLogger(StatusDataProviderImpl.class.getName());

	private String enteSelezionato;

	private List<SelectItem> listaEnti = new ArrayList<SelectItem>();
	private List<String> listaEntiAuth = new ArrayList<String>();
	private List<LogDTO> listaLog = new ArrayList<LogDTO>();

	public List<LogDTO> getLogByRange(int start, int rowNumber) {

		listaLog = new ArrayList<LogDTO>();
		
		try {
			
			List<RCommandLaunch> rcl = new ArrayList<RCommandLaunch>();
			if (enteSelezionato == null || "".equals(enteSelezionato))
				rcl = (List<RCommandLaunch>) recuperaComandoService
						.getRCommandLaunchByRange(listaEntiAuth, start, rowNumber);
			else
				rcl = (List<RCommandLaunch>) recuperaComandoService
						.getRCommandLaunchByBelfiore(enteSelezionato, start,
								rowNumber);

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
					dto.setCommand(recuperaComandoService.getRCommand(cl
							.getFkCommand()));

					listaLog.add(dto);
				}
			}

		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
		}

		return listaLog;
	}

	public long getStatusCount() {

		if (enteSelezionato == null || "".equals(enteSelezionato))
			return recuperaComandoService.getRCommandLaunchCount(listaEntiAuth);
		else
			return recuperaComandoService
					.getRCommandLaunchCountByBelfiore(enteSelezionato);

	}

	public String goStatus() {
		return "controller.status";
	}

	public String getEnteSelezionato() {
		return enteSelezionato;
	}

	public void setEnteSelezionato(String enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}

	public List<SelectItem> getListaEnti() {
		try {
			RulesConnection.getConnection("DEFAULT");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (listaEnti.size() == 0) {
			List<AmComune> listaComuni = getListaEntiAuth();
			for (AmComune comune : listaComuni) {
				listaEntiAuth.add(comune.getBelfiore());
				listaEnti.add(new SelectItem(comune.getBelfiore(), comune
						.getDescrizione()));
			}
		}
		
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

}
