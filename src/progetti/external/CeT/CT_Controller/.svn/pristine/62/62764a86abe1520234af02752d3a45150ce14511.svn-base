package it.webred.ct.rulengine.web.bean.abcomandi;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RCommandLaunch;
import it.webred.ct.rulengine.controller.model.RCommandType;
import it.webred.ct.rulengine.controller.model.REnteEsclusioni;
import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.controller.model.RProcessMonitorPK;
import it.webred.ct.rulengine.controller.model.RTracciaStati;
import it.webred.ct.rulengine.dto.AbComandiDTO;
import it.webred.ct.rulengine.dto.EnteEsclusioniDTO;
import it.webred.ct.rulengine.dto.LogDTO;
import it.webred.ct.rulengine.dto.MonitorDTO;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.web.bean.scheduler.SMonitorBean;
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

public class AbComandiBean extends AbComandiBaseBean implements Serializable {

	private static Logger logger = Logger.getLogger(AbComandiBean.class
			.getName());

	private String tipoOp;
	private String idx;
	private String radio;

	private List<String> listaEntiAuth = new ArrayList<String>(); 
	private List<SelectItem> listaTipoOp = new ArrayList<SelectItem>();
	private List<AbComandiDTO> listaDto = new ArrayList<AbComandiDTO>();
	private List<EnteEsclusioniDTO> listaEsclusioniTot = new ArrayList<EnteEsclusioniDTO>();

	@PostConstruct
	public void init() {

		logger.debug("Inizializzazione");
		doCaricaTipoOp();

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

				// carico primo elemento
				if (lista.size() > 0) {
					tipoOp = lista.get(0).getId().toString();
					doCaricaAbilitazioniByCommandType();
				}

			} catch (Exception e) {
				logger.error("Eccezione: " + e.getMessage(), e);
				super.addErrorMessage("abcomandi.load.error", e.getMessage());
			}
		}
	}

	public void doCaricaAbilitazioniByCommandType() {
		logger.info("Recupero abilitazioni comandi per enti");

		listaDto = new ArrayList<AbComandiDTO>();
		try {
			List<AmComune> listaComuni = getListaEntiAuth();

			List<EnteEsclusioniDTO> listaEE = abComandiService
					.getEnteEsclusioniByCommandType(new Long(tipoOp));
			
			//totali
			listaEsclusioniTot = new ArrayList<EnteEsclusioniDTO>();
			for (EnteEsclusioniDTO ee : listaEE) {

				EnteEsclusioniDTO eeTot = new EnteEsclusioniDTO();
				eeTot.setrCommand(ee.getrCommand());
				eeTot.setrEnteEsclusioni(ee.getrEnteEsclusioni());
				eeTot.setEnable("");
				listaEsclusioniTot.add(eeTot);

			}
			
			//per comune
			for (AmComune comune : listaComuni) {
				listaEntiAuth.add(comune.getBelfiore());
				if (!comune.getBelfiore().equals("9999")) {
					AbComandiDTO dto = new AbComandiDTO();
					dto.setListaEsclusioni(new ArrayList<EnteEsclusioniDTO>());

					for (EnteEsclusioniDTO ee : listaEE) {

						EnteEsclusioniDTO ee2 = new EnteEsclusioniDTO();
						ee2.setrCommand(ee.getrCommand());
						ee2.setrEnteEsclusioni(ee.getrEnteEsclusioni());
						ee2.setEnable("false");
						if ((ee.getrEnteEsclusioni().getEnteOn().equals("*") || ee
								.getrEnteEsclusioni().getEnteOn().contains(
										comune.getBelfiore()))
								&& !ee.getrEnteEsclusioni().getEnteOff()
										.contains(comune.getBelfiore()))
							ee2.setEnable("true");
						dto.getListaEsclusioni().add(ee2);

					}

					dto.setComune(comune);
					listaDto.add(dto);
				}
			}
		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
			super.addErrorMessage("abcomandi.load.error", e.getMessage());
		}
	}

	public void doAttivaDisattivaTutto() {

		try {

			AbComandiDTO enteSelezionato = listaDto.get(Integer.parseInt(idx));
			List<EnteEsclusioniDTO> listaAbilitazioni = enteSelezionato.getListaEsclusioni();

			if (listaAbilitazioni.size() > 0) {

				boolean attivaTutto = listaAbilitazioni.get(0).getEnable()
						.equals("true") ? false : true;
				for (EnteEsclusioniDTO ee : listaAbilitazioni) {

					ee.setEnable(attivaTutto ? "true" : "false");

				}
			}
		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
			super.addErrorMessage("abcomandi.load.error", e.getMessage());
		}

	}
	
	public void doAttivaDisattivaTuttoTotale() {

		try {

			List<EnteEsclusioniDTO> listaAbilitazioni = listaEsclusioniTot;

			if (listaAbilitazioni.size() > 0) {

				boolean attivaTutto = listaAbilitazioni.get(0).getEnable()
						.equals("true") ? false : true;
				for (EnteEsclusioniDTO ee : listaAbilitazioni) {

					ee.setEnable(attivaTutto ? "true" : "false");

				}
			}
		} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
			super.addErrorMessage("abcomandi.load.error", e.getMessage());
		}

	}

	public void doSalvaAbilitazione() {
		logger.info("Salvataggio abilitazione enti");
		String msg = "abcomandi.salva";
		
		try {

			AbComandiDTO enteSelezionato = listaDto.get(Integer.parseInt(idx));
			List<EnteEsclusioniDTO> listaAbilitazioni = enteSelezionato
					.getListaEsclusioni();
			String belfiore = enteSelezionato.getComune().getBelfiore();
			
			for (EnteEsclusioniDTO ee : listaAbilitazioni) {

				Boolean attiva = null;
				if(ee.getEnable().equals("true"))
					attiva = true;
				else if (ee.getEnable().equals("false"))
					attiva = false;
				
				if(attiva != null){
					REnteEsclusioni rEE = ee.getrEnteEsclusioni();
					//aggiungo
					if(attiva && !rEE.getEnteOn().contains(belfiore) && !rEE.getEnteOn().equals("*")){
						if(rEE.getEnteOn().contains("9999"))
							rEE.setEnteOn(rEE.getEnteOn() + "|" + belfiore);
						else
							rEE.setEnteOn("9999|" + belfiore);
					}
					if(attiva && rEE.getEnteOff().contains(belfiore)){
						if(rEE.getEnteOff().equals(belfiore))
							rEE.setEnteOff("*");
						else if(rEE.getEnteOff().startsWith(belfiore))
							rEE.setEnteOff(rEE.getEnteOff().replace(belfiore + "|", ""));
						else rEE.setEnteOff(rEE.getEnteOff().replace("|" + belfiore, ""));
					}
					
					//elimino
					if(!attiva && rEE.getEnteOn().contains(belfiore)){
						rEE.setEnteOn(rEE.getEnteOn().replace("|" + belfiore, ""));
					}
					if(!attiva && rEE.getEnteOn().equals("*")){
						if(rEE.getEnteOff().equals("*"))
							rEE.setEnteOff(belfiore);
						else if (!rEE.getEnteOff().contains(belfiore))
							rEE.setEnteOff(rEE.getEnteOff() + "|" + belfiore);
					}
	
					abComandiService.updateEnteEsclusioni(rEE);
				}
			}

			
			super.addInfoMessage(msg);
			
			//update
			doCaricaAbilitazioniByCommandType();
			
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}
	}
	
	public void doSalvaAbilitazioneTotale() {
		logger.info("Salvataggio abilitazione tutti enti");
		String msg = "abcomandi.salva";
		
		try {
			
			for (EnteEsclusioniDTO ee : listaEsclusioniTot) {

				Boolean attiva = null;
				if(ee.getEnable().equals("true"))
					attiva = true;
				else if (ee.getEnable().equals("false"))
					attiva = false;
				
				if(attiva != null){
					REnteEsclusioni rEE = ee.getrEnteEsclusioni();
					//aggiungo
						//rEE.setEnteOn("*");
						//rEE.setEnteOff("*");
					for(String belfiore: listaEntiAuth){
						if(attiva && !rEE.getEnteOn().contains(belfiore) && !rEE.getEnteOn().equals("*")){
							if(rEE.getEnteOn().contains("9999"))
								rEE.setEnteOn(rEE.getEnteOn() + "|" + belfiore);
							else
								rEE.setEnteOn("9999|" + belfiore);
						}
						if(attiva && rEE.getEnteOff().contains(belfiore)){
							if(rEE.getEnteOff().equals(belfiore))
								rEE.setEnteOff("*");
							else if(rEE.getEnteOff().startsWith(belfiore))
								rEE.setEnteOff(rEE.getEnteOff().replace(belfiore + "|", ""));
							else rEE.setEnteOff(rEE.getEnteOff().replace("|" + belfiore, ""));
						}
						
						//elimino
						if(!attiva && rEE.getEnteOn().contains(belfiore)){
							rEE.setEnteOn(rEE.getEnteOn().replace("|" + belfiore, ""));
						}
						if(!attiva && rEE.getEnteOn().equals("*")){
							if(rEE.getEnteOff().equals("*"))
								rEE.setEnteOff(belfiore);
							else if (!rEE.getEnteOff().contains(belfiore))
								rEE.setEnteOff(rEE.getEnteOff() + "|" + belfiore);
						}
					}
	
					abComandiService.updateEnteEsclusioni(rEE);
				}
			}

			
			super.addInfoMessage(msg);
			
			//update
			doCaricaAbilitazioniByCommandType();
			
		} catch (Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}
	}

	public String goAbComandi() {
		return "controller.abcomandi";
	}

	public String getTipoOp() {
		return tipoOp;
	}

	public void setTipoOp(String tipoOp) {
		this.tipoOp = tipoOp;
	}

	public List<SelectItem> getListaTipoOp() {
		return listaTipoOp;
	}

	public void setListaTipoOp(List<SelectItem> listaTipoOp) {
		this.listaTipoOp = listaTipoOp;
	}

	public List<AbComandiDTO> getListaDto() {
		return listaDto;
	}

	public void setListaDto(List<AbComandiDTO> listaDto) {
		this.listaDto = listaDto;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public List<EnteEsclusioniDTO> getListaEsclusioniTot() {
		return listaEsclusioniTot;
	}

	public void setListaEsclusioniTot(List<EnteEsclusioniDTO> listaEsclusioniTot) {
		this.listaEsclusioniTot = listaEsclusioniTot;
	}

}
