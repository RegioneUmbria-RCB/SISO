package it.webred.ct.rulengine.web.bean.eventi;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.rulengine.controller.model.RAnagEventi;
import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RCommandType;
import it.webred.ct.rulengine.controller.model.REventiEnte;
import it.webred.ct.rulengine.controller.model.REventiEntePK;
import it.webred.ct.rulengine.web.bean.eventi.exception.EventoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

public class ENewBean extends EventiBaseBean implements Serializable {
	private static Logger logger = Logger.getLogger(ENewBean.class.getName());
	
	private List<SelectItem> listaEnti = new ArrayList<SelectItem>();
	private List<SelectItem> listaFonti = new ArrayList<SelectItem>();
	private List<SelectItem> listaTipoOp = new ArrayList<SelectItem>();
	private List<SelectItem> listaComandi = new ArrayList<SelectItem>();
	
	private String ente;
	private String tipoOp;
	private String fonte;
	private String comando;
	
	
	private List<SelectItem> listaComandiAfter = new ArrayList<SelectItem>();
	private List<SelectItem> listaStatiAfter = new ArrayList<SelectItem>();
	
	private String dopoOper;
	private String dopoFd;
	private String dopoCmd;
	private String dopoStato;
	
	private boolean operDisabled;
	private boolean fdDisabled;
	private boolean cdmDisabled;
	private boolean statoDisabled;
	
	private String newEvtPage = "/jsp/protected/empty.xhtml";

	
	
	public void doInitNewEvent() {
		logger.debug("Inizializzazione");
		
		doCaricaEnti();
		if (listaEnti.size() > 0)
			ente = (String) listaEnti.get(0).getValue();
		
		doCaricaTipoOp();
		if (listaTipoOp.size() > 0) {
			tipoOp = ((Long) listaTipoOp.get(0).getValue()).toString();
		}
		
		//if (ente != null)
		doCaricaFonti();
		if (listaFonti.size() > 0) {
			fonte = listaFonti.get(0).getValue().toString();
		}
		
		doCaricaComandi();
		if (listaComandi.size() > 0)
			comando = listaComandi.get(0).getValue().toString();
		
		
		//caricamente liste opzioni
		loadStati();
		
		dopoOper = "0";
		dopoFd = "0";
		dopoCmd = "0";
		dopoStato = "0";
		
		operDisabled = false;
		fdDisabled = false;
		cdmDisabled = false;
		statoDisabled = false;
	}
	
	
	
	
	
	public void doCaricaEnti() {
		logger.info("Recupero enti legate all'utente");

		if (listaEnti.size() == 0) {
			try {
				List<AmComune> listaComuni = getListaEntiAuth();
				for (AmComune comune : listaComuni) {
					listaEnti.add(new SelectItem(comune.getBelfiore(), comune.getDescrizione()));
				}
			} catch (Exception e) {
				logger.error("Eccezione: " + e.getMessage(), e);
			}
		}
	}
	
	
	public void doCaricaFonti() {

		try {
			
			if (ente != null && !ente.equals("9999")&& !ente.equals("")) {
				logger.info("Recupero fonti dati legate all'ente");
				List<AmFonteComune> lista = comuneService.getListaFonteByComune(ente);
				listaFonti = new ArrayList<SelectItem>();

				for (AmFonteComune fc : lista) {
					SelectItem item = new SelectItem(fc.getId().getFkAmFonte(), 
									fc.getAmFonte().getDescrizione());
					listaFonti.add(item);
				}
			}
			else {
				logger.info("Recupero tutte le fonti dati");
				List<AmFonte> fonti = fonteService.getListaFonteAll();
				listaFonti = new ArrayList<SelectItem>();
				
				for (AmFonte f : fonti) {
					SelectItem item = new SelectItem(f.getId(), f.getDescrizione());
					listaFonti.add(item);
				}
			}

			// carico comandi
			if (listaFonti.size() > 0) {
				fonte = listaFonti.get(0).getValue().toString();
				
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
				List<RCommandType> lista = mainControllerService.getListRCommandType();
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
	
	
	
	/*
	 * GESTIONE REQUISITI
	 */
	
	public void doCaricaComandiAfter() {
		
		listaComandiAfter = new ArrayList<SelectItem>();
		
		//List<RCommand> rCommandList = recuperaComandoService.getAllRCommands();
		
		if (ente != null && dopoOper != null) {
		
			try {
				// recupero comandi
				List<RCommand> rCommandList = new ArrayList<RCommand>();
				if(dopoFd != null && !dopoFd.equals("") && !dopoFd.equals("0")){
					List<Long> listaFonti = new ArrayList<Long>();
					listaFonti.add(new Long(dopoFd));
					rCommandList.addAll(recuperaComandoService.getRCommandsByFontiAndType(ente, listaFonti, new Long(dopoOper)));
				}
				
				rCommandList.addAll(recuperaComandoService.getRCommandsByTypeWithoutFonti(ente, new Long(dopoOper)));
				
				for(RCommand rCommand: rCommandList) {
					//tutti i comandi non dummy
					if(rCommand.getSystemCommand().intValue() != 3) {
						SelectItem item = new SelectItem(rCommand.getId(), rCommand.getDescr());
						listaComandiAfter.add(item);
					}
				}
			} catch (Exception e) {
				logger.error("Eccezione: " + e.getMessage(), e);
			}
		}
		
		/*if (ente != null && !ente.equals("9999")&& !ente.equals("")) {
			for(RCommand rCommand: rCommandList) {
				if(recuperaComandoService.getOFF(rCommand.getId(), ente) != null) {
					if(recuperaComandoService.getON(rCommand.getId(), ente) == null) {
						logger.debug("L'ente "+ente+" non ha abilitato il comando "+rCommand.getCodCommand());
						continue;
					}
				}
				
				SelectItem item = new SelectItem(rCommand.getId(), rCommand.getDescr());
				listaComandiAfter.add(item);
			}
		}
		else {
			for(RCommand rCommand: rCommandList) {
				SelectItem item = new SelectItem(rCommand.getId(), rCommand.getDescr());
				listaComandiAfter.add(item);
			}
		}*/
	}
	
	
	private void loadStati() {
		
		listaStatiAfter = new ArrayList<SelectItem>();
		
		List<RAnagStati> stati = mainControllerService.getAllStati();
		
		if(dopoCmd != null && !dopoCmd.equals("0")) {
			RCommand rc = recuperaComandoService.getRCommand(new Long(dopoCmd));
			stati = mainControllerService.getStati(rc.getRCommandType().getId());
		}
		else {
			stati = mainControllerService.getAllStati();
		}
		
		for(RAnagStati ras: stati) {
			SelectItem item = new SelectItem(ras.getId(), ras.getDescr());
			listaStatiAfter.add(item);
		}
	}
	
	public void changeCmd() {
		if(!dopoCmd.equals("0")) {
			operDisabled = true;
			fdDisabled = true;
		}
		else {
			//se anche stato è 0...
			if(dopoStato.equals("0")) {
				operDisabled = false;
				fdDisabled = false;
			}
			else {
				operDisabled = true;
				fdDisabled = true;
			}
		}
		
		loadStati();
	}
	
	public void changeStato() {
		if(!dopoStato.equals("0")) {
			operDisabled = true;
			fdDisabled = true;
		}
		else {
			//se anche cmd è 0...
			if(dopoCmd.equals("0")) {
				operDisabled = false;
				fdDisabled = false;
			}
			else {
				operDisabled = true;
				fdDisabled = true;
			}
		}
	}
	
	
	
	public void doSalvaEvento() {
		logger.info("Salvataggio evento");
		String msg = "eventi.salva";
				
		
		try {
			//controllo comando evento
			if(comando == null) 
				throw new EventoException("Comando evento");
			
			
			//controllo sui comandi after
			if(dopoOper.equals("0") && dopoFd.equals("0") && dopoCmd.equals("0") && dopoStato.equals("0")) {
				msg = "eventi.salva.after";
				throw new EventoException("");
			}
			
			//salavatggio
			RAnagEventi rav = new RAnagEventi();
			
			if(ente != null && !ente.equals("9999")&& !ente.equals(""))  
				rav.setStandard("N");
			else 
				rav.setStandard("Y");
			
			
			//impostazione requisiti evento
			if(!dopoOper.equals("0")) {
				rav.setRCommandType(mainControllerService.getRCommandType(new Long(dopoOper)));
			}
				
			if(!dopoFd.equals("0")) {
				rav.setAfterIdFonte(new Long(dopoFd));
			}
			
			if(!dopoCmd.equals("0")) {
				rav.setAfterCommand(new Long(dopoCmd));
			}
			
			if(!dopoStato.equals("0")) {
				rav.setRAnagStati(mainControllerService.getRAnagStato(new Long(dopoStato)));
			}
			
			//impostazioni comando evneto
			rav.setRCommand(recuperaComandoService.getRCommand(new Long(comando)));
			
			//salavatggio
			super.eventService.salvaNuovoEvento(rav);
			
			EMonitorBean emb = (EMonitorBean)super.getBeanReference("eMonitorBean");
			
			//refresh lista eventi
			emb.doCaricaListaEventi();
					
			resetPage();
			super.addInfoMessage(msg);
		}catch(EventoException ee) {
			super.addErrorMessage(msg+".field.error",ee.getMessage());
		}catch(Exception e) {
			super.addErrorMessage(msg + ".error", e.getMessage());
			logger.error("Eccezione: " + e.getMessage(), e);
		}
	}
	
	
	
	public void resetPage() {
		newEvtPage = "/jsp/protected/empty.xhtml";
		
		operDisabled = false;
		fdDisabled = false;
		cdmDisabled = false;
		statoDisabled = false;
	}

	
	
	private void autoAbilita(Long idEvento,String std,String enteSel) {
		REventiEnte ree = new REventiEnte();
		REventiEntePK pk = new REventiEntePK();
		ree.setId(pk);
		
		pk.setId(idEvento);
		ree.setStandard(std);
		pk.setBelfiore(enteSel);
		eventService.abilitaEvento(ree);
		logger.info("Evento "+idEvento+"@"+std+" abilitato ["+enteSel+"]");
	}
	
	
	
	public String getNewEvtPage() {
		return newEvtPage;
	}

	public void setNewEvtPage(String newEvtPage) {
		this.newEvtPage = newEvtPage;
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


	public List<SelectItem> getListaEnti() {
		return listaEnti;
	}


	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}





	public String getDopoOper() {
		return dopoOper;
	}





	public void setDopoOper(String dopoOper) {
		this.dopoOper = dopoOper;
	}





	public String getDopoFd() {
		return dopoFd;
	}





	public void setDopoFd(String dopoFd) {
		this.dopoFd = dopoFd;
	}





	public String getDopoCmd() {
		return dopoCmd;
	}





	public void setDopoCmd(String dopoCmd) {
		this.dopoCmd = dopoCmd;
	}





	public String getDopoStato() {
		return dopoStato;
	}





	public void setDopoStato(String dopoStato) {
		this.dopoStato = dopoStato;
	}





	public boolean isCdmDisabled() {
		return cdmDisabled;
	}





	public void setCdmDisabled(boolean cdmDisabled) {
		this.cdmDisabled = cdmDisabled;
	}





	public boolean isOperDisabled() {
		return operDisabled;
	}





	public void setOperDisabled(boolean operDisabled) {
		this.operDisabled = operDisabled;
	}





	public boolean isFdDisabled() {
		return fdDisabled;
	}





	public void setFdDisabled(boolean fdDisabled) {
		this.fdDisabled = fdDisabled;
	}





	public boolean isStatoDisabled() {
		return statoDisabled;
	}





	public void setStatoDisabled(boolean statoDisabled) {
		this.statoDisabled = statoDisabled;
	}





	public List<SelectItem> getListaComandiAfter() {
		return listaComandiAfter;
	}





	public void setListaComandiAfter(List<SelectItem> listaComandiAfter) {
		this.listaComandiAfter = listaComandiAfter;
	}





	public List<SelectItem> getListaStatiAfter() {
		return listaStatiAfter;
	}





	public void setListaStatiAfter(List<SelectItem> listaStatiAfter) {
		this.listaStatiAfter = listaStatiAfter;
	}






}
