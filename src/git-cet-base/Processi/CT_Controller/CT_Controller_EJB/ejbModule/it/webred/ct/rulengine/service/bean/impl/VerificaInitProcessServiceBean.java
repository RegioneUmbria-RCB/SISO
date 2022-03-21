package it.webred.ct.rulengine.service.bean.impl;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import it.webred.ct.config.model.AmFonteComune;

import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.controller.model.RProcessMonitorPK;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.service.ControllerBaseService;

import it.webred.ct.rulengine.service.bean.VerificaInitProcessService;
import it.webred.ct.rulengine.service.bean.verifica.handler.VerificaHandler;
import it.webred.ct.rulengine.service.bean.verifica.handler.dto.VerificaHandlerInfo;
import it.webred.ct.rulengine.service.bean.verifica.handler.dto.VerificaHandlerParams;
import it.webred.ct.rulengine.service.exception.ServiceException;
import it.webred.ct.rulengine.service.exception.VerificaException;

import javax.ejb.Stateless;


/**
 * Session Bean implementation class VerificaInitProcessServiceBean
 */
@Stateless
public class VerificaInitProcessServiceBean extends ControllerBaseService implements VerificaInitProcessService {
	
	
	
	private Properties verificaProps = null;
	
    /**
     * Default constructor. 
     */
    public VerificaInitProcessServiceBean() {
        try {
        	ClassLoader loader = Thread.currentThread().getContextClassLoader();
        	URL url = loader.getResource("it/webred/ct/rulengine/service/bean/verifica/handler/verifica.properties");
        	logger.debug("URL ["+url+"]");
        	verificaProps = new Properties();
        	verificaProps.load(url.openStream());
        }catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
    }
    
    /**
     * Metodo di verifica dei processi Reperimento e Acquisizione che prevede
     * il recupero dell'oggetto RCommand in base a ente|FD|tipo_operazione
     * 
     */
	public Task verificaInizialeProcesso(Task task) {
		Task newTask = task.getCopy();
		
		try {
			logger.debug("Inizio controllo sulla FD coinvolta");
			
			//recupero comando
			RCommand rCommand = (RCommand)task.getFreeObj();
			newTask.setIdTipologia(rCommand.getRCommandType().getId());
			
			//recupero eventuali processi lock
			List<RProcessMonitor> lRPM = processMonitorService.getLockedProcessMonitor(task.getBelfiore(),task.getIdFonte());
			
			//recupero esito processi precedenti sulla FD
			List<RProcessMonitor> prevProcessFDs = processMonitorService.getPrevProcessMonitor(task.getBelfiore(),task.getIdFonte());
			
			//recupero stato attuale del processo sulla FD
			RProcessMonitor rProcessMonitor = processMonitorService.getProcessMonitor(new RProcessMonitorPK(task.getBelfiore(),rCommand.getId()));
			
			//parametri per metodi di verifica
			VerificaHandlerParams vhp = new VerificaHandlerParams(lRPM,prevProcessFDs,rProcessMonitor);
			
			
			//controlli
			String classname = verificaProps.getProperty("verifica.handler."+task.getIdTipologia()+".class");
			logger.debug("Caricamento classe verifica handler ["+classname+"]");
			Class c = Class.forName(classname);
			VerificaHandler vh = (VerificaHandler)c.newInstance();
			Object[] args = {vhp};
			 
			//impostazione di default 
			newTask.setProcessable(true);
			Method[] methods = c.getDeclaredMethods();
			for(Method m: methods) {
				logger.debug("Invoke del metodo "+m.getName());
				VerificaHandlerInfo vhi = (VerificaHandlerInfo)m.invoke(vh, args);
				if(!vhi.getMessage().equals(""))
					newTask.setStatus(vhi.getMessage());
				if(!vhi.getEsito()) {
					logger.warn("Processo non eseguibile al momento, la verifica ha prodotto una segnalazione");
					//aggiornamento task con info down
					newTask.setProcessable(false);
					break;
				}
			}
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return newTask;
	}

	/**
     * Metodo di verifica dei processi di Trattamento che prevede la presenza dell'oggetto
     * RCommand dentro Task
     * 
     */
	public Task verificaInizialeProcessiTrattamento(Task task) {
		Task newTask = task.getCopy();
		
		try {
			//analisi comando
			RCommand rCommand = (RCommand)task.getFreeObj();
			newTask.setIdTipologia(rCommand.getRCommandType().getId());
			
			//recupero FD legate al comando
			List<RFontedatiCommand> fds = recuperaComandoService.getRCommandFDs(rCommand);
			
			//recupero delle sole fd che tratta l'ente in esame
			List<AmFonteComune> enteFDs = comuneService.getListaFonteByComune(task.getBelfiore());
			
			//per ogni fd vanno eseguiti i controlli
			logger.info("Inizio controllo su tutte le FD coinvolte nel processo "+rCommand.getCodCommand());
			for(RFontedatiCommand fd: fds) {
				logger.debug("Fonte dati da verificare: "+fd.getId().getIdFonte());
				
				//considero le sole fd che l'ente tratta
				for(AmFonteComune amFD: enteFDs) {
					
					Long currEnteAmFD = new Long(amFD.getId().getFkAmFonte());
					
					if(currEnteAmFD.longValue() == fd.getId().getIdFonte().longValue()) {
						logger.debug("Fonte dati ["+fd.getId().getIdFonte()+
									 "] trattata dall'ente corrente");
					}
					else {
						//logger.debug("Fonte dati "+fd.getId().getIdFonte()+" [comando] diversa da "+currEnteAmFD+" [ente]");
						continue;
					}

					//recupero eventuali processi lock
					List<RProcessMonitor> lRPM = processMonitorService.getLockedProcessMonitor(task.getBelfiore(),fd.getId().getIdFonte());
					
					//recupero esito processi precedenti sulla FD
					List<RProcessMonitor> prevProcessFDs = processMonitorService.getPrevProcessMonitor(task.getBelfiore(),fd.getId().getIdFonte());
					
					//recupero stato attuale del processo sulla FD
					RProcessMonitor rProcessMonitor = processMonitorService.getProcessMonitor(new RProcessMonitorPK(task.getBelfiore(),rCommand.getId()));
					
					//parametri per metodi di verifica
					VerificaHandlerParams vhp = new VerificaHandlerParams(lRPM,prevProcessFDs,rProcessMonitor);
					
					//controlli
					String classname = verificaProps.getProperty("verifica.handler."+rCommand.getRCommandType().getId()+".class");
					logger.debug("Caricamento classe verifica handler ["+classname+"]");
					Class c = Class.forName(classname);
					VerificaHandler vh = (VerificaHandler)c.newInstance();
					Object[] args = {vhp};
					
					//impostazione di default 
					newTask.setProcessable(true);
					Method[] methods = c.getDeclaredMethods();
					for(Method m: methods) {
						logger.debug("Invoke del metodo "+m.getName());
						VerificaHandlerInfo vhi = (VerificaHandlerInfo)m.invoke(vh, args);
						if(!vhi.getMessage().equals(""))
							newTask.setStatus(vhi.getMessage());
						if(!vhi.getEsito()) {
							logger.warn("Processo " + rCommand.getCodCommand() + " non eseguibile al momento, la verifica ha prodotto una segnalazione");
							//aggiornamento task con info down
							newTask.setProcessable(false);
							break;
						}
					}
					
					//uscita dal ciclo interno
					if(!newTask.isProcessable()) {	
						throw new VerificaException("Processo " +  rCommand.getCodCommand() +  " non eseguibile !!");
					}
				}
				
				//uscita dal ciclo interno
				//if(!newTask.isProcessable()) {	break; }
			}
			
			//se il comando non è legato ad una fonte
			if(newTask.getStatus() == null){

				//recupero stato attuale del processo
				RProcessMonitor rProcessMonitor = processMonitorService.getProcessMonitor(new RProcessMonitorPK(task.getBelfiore(),rCommand.getId()));
				
				//parametri per metodi di verifica
				VerificaHandlerParams vhp = new VerificaHandlerParams(new ArrayList<RProcessMonitor>(),new ArrayList<RProcessMonitor>(),rProcessMonitor);
				
				//controlli
				String classname = verificaProps.getProperty("verifica.handler."+rCommand.getRCommandType().getId()+".class");
				logger.debug("Caricamento classe verifica handler ["+classname+"]");
				Class c = Class.forName(classname);
				VerificaHandler vh = (VerificaHandler)c.newInstance();
				Object[] args = {vhp};
				
				//impostazione di default 
				newTask.setProcessable(true);
				Method[] methods = c.getDeclaredMethods();
				for(Method m: methods) {
					logger.debug("Invoke del metodo "+m.getName());
					VerificaHandlerInfo vhi = (VerificaHandlerInfo)m.invoke(vh, args);
					if(!vhi.getMessage().equals(""))
						newTask.setStatus(vhi.getMessage());
					if(!vhi.getEsito()) {
						logger.warn("Processo" +   rCommand.getCodCommand() + " non eseguibile al momento, la verifica ha prodotto una segnalazione");
						//aggiornamento task con info down
						newTask.setProcessable(false);
						break;
					}
				}
				
				//uscita dal ciclo interno
				if(!newTask.isProcessable()) {	
					throw new VerificaException("Processo" +   rCommand.getCodCommand() + " non eseguibile !!");
				}
			}
			
		}catch(VerificaException ve) {
			logger.warn(ve.getMessage());
		}catch(Throwable t) {
			logger.error("Eccezione", t);
			throw new ServiceException(t);
		}
		
		return newTask;
	}

	
	
	
	
	
	
}
