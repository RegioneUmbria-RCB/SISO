package it.webred.ct.rulengine.web.bean.eventi;



import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.rulengine.controller.model.RAnagEventi;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.REventiEnte;
import it.webred.ct.rulengine.controller.model.REventiEntePK;
import it.webred.ct.rulengine.dto.EventoDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

public class EMonitorBean extends EventiBaseBean implements Serializable {
	private static Logger logger = Logger.getLogger(EMonitorBean.class.getName());
	
	private List<SelectItem> listaEnti = new ArrayList<SelectItem>();
	private List<EventoDTO> listaEventi = new ArrayList<EventoDTO>();
	
	
	private String enteSelezionato;
	private Long id;
	private String std;
	
	private String eventiLogPage = "/jsp/protected/empty.xhtml";
	
	private boolean on;
	
	public String goEventi() {
		logger.debug("Inizializzazione Eventi");
		if (listaEnti.size() == 0) {
			List<AmComune> listaComuni = getListaEntiAuth();
			for (AmComune comune : listaComuni) {
				listaEnti.add(new SelectItem(comune.getBelfiore(), comune.getDescrizione()));
			}
		}
		//impostazione visualizzazione ente 9999
		enteSelezionato = (String) listaEnti.get(0).getValue();
		
		doCaricaListaEventi();
		return "controller.eventi";
		
	}

	
	public void doCaricaListaEventi() {
		logger.debug("Caricamento lista eventi");
		listaEventi = new ArrayList<EventoDTO>();
		
		try {
			//tutti eventi
			List<RAnagEventi> anagEventi = eventService.getEventi();
			
			if(anagEventi != null) {
				
				//tutti gli eventi abilitati dall'ente (sia std (Y) che non (N))
				List<RAnagEventi> angaEventiEnte = new ArrayList<RAnagEventi>();
				if (enteSelezionato != null && !enteSelezionato.equals("9999") && !enteSelezionato.equals("")) {
					//recupero dei solo eventi associati all'ente selezionato
					angaEventiEnte = eventService.getEventiEnte(enteSelezionato);
				}
				
				for(RAnagEventi rae: anagEventi) {
					EventoDTO dto = new EventoDTO();				
					
					//controllo quali eventi sono abilitati
					for(RAnagEventi item: angaEventiEnte) {
						long itemID = item.getId().longValue();
						long raeID = rae.getId().longValue();
						
						String itemSTD = item.getStandard();
						String raeSTD = rae.getStandard();
						
						if(itemID == raeID && itemSTD.equals(raeSTD)) {
							//evento presente e quindi abilitato
							dto.setSelected(true);
							break;
						}
					}
					
					dto.setId(rae.getId());
					dto.setStandard(rae.getStandard());
					
					//recupero next command
					dto.setNextCommand(rae.getRCommand());
					
					//recupero info command type
					if(rae.getRCommandType() != null) {
						dto.setAfterRCommandType(rae.getRCommandType());
					}
					
					//recupero info fonte dati
					if(rae.getAfterIdFonte() != null) {
						AmFonte fonte = fonteService.getFonte(rae.getAfterIdFonte());
						
						//se ente selezionato controllo che la fd sia abilitata
						if (enteSelezionato != null && !enteSelezionato.equals("9999")) {
							boolean found = false;
							List<AmFonteComune> fdComune = comuneService.getListaFonteByComune(enteSelezionato);
							for(AmFonteComune fd: fdComune) {
								if(fd.getAmFonte().getId().intValue() == fonte.getId().intValue()) {
									found = true;
									break;
								}
							}
							
							if(!found){ 
								logger.warn("L'ente "+enteSelezionato+" non ha abilitato la fonte dati "+
														fonte.getDescrizione()+": evento non consentito");
								continue;
							}
						}
						
						dto.setAfterIdFonte(new Long(fonte.getId()));
						dto.setAfterFonteName(fonte.getDescrizione());
					}
					
					//recupero info command
					if(rae.getAfterCommand() != null) {
						RCommand rCommand = recuperaComandoService.getRCommand(rae.getAfterCommand());
						
						//se ente selezionato controllo che il comando sia abilitato
						if (enteSelezionato != null && !enteSelezionato.equals("9999") && rCommand != null) {
							RCommand rcOff = recuperaComandoService.getOFF(rCommand.getId(), enteSelezionato);
							if(rcOff != null) {
								RCommand rcOn = recuperaComandoService.getON(rCommand.getId(), enteSelezionato);
								if(rcOn == null) {
									logger.warn("L'ente "+enteSelezionato+" non ha abilitato il comando "+
														rCommand.getCodCommand()+": evento non consentito");
									continue;
								}
							}
						}
						
						dto.setAfterRCommand(rCommand);
					}
					
					//recupero info stato
					if(rae.getRAnagStati() != null) {
						dto.setAfterRAnagStato(rae.getRAnagStati());
					}
					
					listaEventi.add(dto);
				}
				
			}
			
		}catch(Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
			super.addErrorMessage("eventi.lista.error", e.getMessage());
		}
	}
	
	
	//abilita/disabilita
	public void doAbilita() {
		
		if(!on) {
			REventiEnte ree = new REventiEnte();
			REventiEntePK pk = new REventiEntePK();
			ree.setId(pk);
			
			pk.setId(id);
			ree.setStandard(std);
			pk.setBelfiore(enteSelezionato);
			eventService.abilitaEvento(ree);
			logger.info("Evento "+id+"@"+std+" abilitato ["+enteSelezionato+"]");
		}
		else {
			REventiEntePK pk = new REventiEntePK();
			pk.setId(id);
			pk.setBelfiore(enteSelezionato);
			
			eventService.disabilitaEvento(pk);
			logger.info("Evento "+id+"@"+std+" disabilitato ["+enteSelezionato+"]");
		}
	}
	
	
	public void doDeleteEvento() {
		logger.info("Eliminazione evento "+id);
		
		try {

			eventService.deleteEvento(id);
			
			//refresh lista
			doCaricaListaEventi();
			
		}catch(Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
			super.addErrorMessage("eventi.lista.error", e.getMessage());
		}
	}
	
	
	public String getEventiLogPage() {
		return eventiLogPage;
	}

	public void setEventiLogPage(String eventiLogPage) {
		this.eventiLogPage = eventiLogPage;
	}



	public List<SelectItem> getListaEnti() {
		return listaEnti;
	}



	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}



	public String getEnteSelezionato() {
		return enteSelezionato;
	}



	public void setEnteSelezionato(String enteSelezionato) {
		this.enteSelezionato = enteSelezionato;
	}




	public boolean isOn() {
		return on;
	}


	public void setOn(boolean on) {
		this.on = on;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getStd() {
		return std;
	}


	public void setStd(String std) {
		this.std = std;
	}


	public List<EventoDTO> getListaEventi() {
		return listaEventi;
	}


	public void setListaEventi(List<EventoDTO> listaEventi) {
		this.listaEventi = listaEventi;
	}
}
