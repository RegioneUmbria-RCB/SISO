package it.webred.ct.rulengine.service.bean.impl;

import java.util.ArrayList;
import java.util.List;

import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RCommandAck;
import it.webred.ct.rulengine.controller.model.RCommandLaunch;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.ct.rulengine.service.exception.ServiceException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class RecuperaComandoServiceBean
 */
@Stateless
public class RecuperaComandoServiceBean implements RecuperaComandoService {	
	protected Logger logger = Logger.getLogger(RecuperaComandoServiceBean.class.getName());
	
	@PersistenceContext(unitName = "Controller_Model")
	private EntityManager manager;
	
	
	public RCommand getRCommandDummy(Long idCommandType,Long idFonte) {
		RCommand rCommand = null;
    	
    	try {
    		
    		Query q = manager.createNamedQuery("Controller.getCommandDummy");
    		q.setParameter("idCommandType", idCommandType);
    		q.setParameter("idFonte", idFonte);
    		
    		rCommand = (RCommand)q.getSingleResult();
    		
    	}catch(NoResultException nre) {
    		logger.warn("Nessun comando dummy trovato [command type : "+idCommandType+"]" + "[" + idFonte + "]");
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage());
    		throw new ServiceException(t);
    	}
    	
    	return rCommand;
	}
	
	/**
	 * Recupero RCommand a partire da codCommand
	 */
	public RCommand getRCommand(String codCommand) {
		RCommand rCommand = null;
    	
    	try {
    		
    		Query q = manager.createNamedQuery("Controller.getCommand");
    		q.setParameter("codCommand", codCommand);
    		
    		rCommand = (RCommand)q.getSingleResult();
    		
    	}catch(NoResultException nre) {
    		logger.warn("Nessun comando trovato [cod. command : "+codCommand+"]");
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage());
    		throw new ServiceException(t);
    	}
    	
    	return rCommand;
	}


	/**
	 * Il metodo restituisce il comando relativo ad un ente che tratta una particolare FD
	 * secondo un certo tipo di elaborazione
	 */
	public RCommand getRCommand(Task task) {
    	
    	RCommand rCommand = null;
    	
    	try {
    		logger.debug("Recupero comando [Ente: "+
    								task.getBelfiore()+" Fonte dati: "+
    								task.getIdFonte().intValue()+" Operazione: "+
    								task.getIdTipologia()+"]");
    		
    		Query q = manager.createNamedQuery("Controller.getCommands");
    		q.setParameter("idFonte", task.getIdFonte());
    		q.setParameter("idCommandType", task.getIdTipologia());
    		
    		List<RCommand> rcs =  q.getResultList();
    		
    		int cnt = rcs.size();
    		logger.debug("Size: "+rcs.size());
    		
    		switch(cnt) {
	    		case 0: {
	    			logger.info("Nessun comando presente");
	    			break;
	    		}
	    		case 1: {
	    			rCommand = (RCommand)rcs.get(0);
	    			
	    			//controllo esclusione
	    			RCommand rcOff = getOFF(rCommand.getId(),task.getBelfiore());
	    			if(rcOff != null) {
	    				RCommand rcOn = getON(rCommand.getId(),task.getBelfiore());
	    				if(rcOn != null) {
	    					rCommand = rcOn;
	    					logger.debug("ON trovato !");
	    				}
	    				else {
	    					//in base al tipo restituire il comando dummy
	    					rCommand = getRCommandDummy(rCommand.getRCommandType().getId(),task.getIdFonte());
	    					logger.debug("Comando dummy restituito ["+ (rCommand == null ? "NULL" : rCommand.getCodCommand()) +"]");
	    				}
	    			}
	    			else {
	    				logger.info("Comando trovato ["+rCommand.getCodCommand()+"]");	
	    			}
	    			
	    			break;
	    		}
	    		default: {
	    			logger.debug("Tanti comandi....ciclare !!");
	    			
	    			for(RCommand rc: rcs) {
	    				
	    				//escludere i dummy
	    				if(rc.getSystemCommand().intValue() == 3) 
	    					continue;
	    				
	    				//controllo esclusione
		    			RCommand rcOff = getOFF(rc.getId(),task.getBelfiore());
		    			if(rcOff != null) {
		    				//controllo attivazione forzata
		    				RCommand rcOn = getON(rc.getId(),task.getBelfiore());
		    				if(rcOn != null) {
		    					rCommand = rcOn;
		    					logger.info("Comando attivato ["+rCommand.getCodCommand()+"]");
		    					break;
		    				}
		    			}
		    			else {
		    				rCommand = rc;
		    				logger.info("Comando attivato ["+rCommand.getCodCommand()+"]");	
		    				break;
		    			}
	    			}
	    			
	    			//cotrollo comando trovato
	    			if(rCommand == null) {
	    				//dummy
	    				rCommand = getRCommandDummy(task.getIdTipologia(),task.getIdFonte());
    					logger.debug("Comando dummy restituito ["+rCommand.getCodCommand()+"]");
	    			}
	    			
	    			break;
	    		}
    		}
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
    	
    	return rCommand;
	}

	
	public RCommand getON(Long idCommand,String belfiore) {
		RCommand rCommand = null;
    	
    	try {
    		
    		Query q = manager.createNamedQuery("Controller.getFDCommandEnteON");
    		q.setParameter("idCommand", idCommand);
    		q.setParameter("idEnte", "%"+belfiore+"%");
    		
    		rCommand = (RCommand)q.getSingleResult();
    		
    	}catch(NoResultException nre) {
    		logger.warn("Nessun comando custom per l'ente "+belfiore+" [command ID: "+idCommand+"]");
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage());
    		throw new ServiceException(t);
    	}
    	
    	return rCommand;
	}
	
	
	public RCommand getOFF(Long idCommand,String belfiore) {
		RCommand rCommand = null;
    	
    	try {
    		
    		Query q = manager.createNamedQuery("Controller.getFDCommandEnteOFF");
    		q.setParameter("idCommand", idCommand);
    		q.setParameter("idEnte", "%"+belfiore+"%");
    		
    		rCommand = (RCommand)q.getSingleResult();
    		
    	}catch(NoResultException nre) {
    		logger.warn("Comando non escluso dall'ente "+belfiore+" [command ID: "+idCommand+"]");
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage());
    		throw new ServiceException(t);
    	}
    	
    	return rCommand;
	}


	public List<RFontedatiCommand> getRCommandFDs(RCommand rCommand) {
		
		List<RFontedatiCommand> list = null;
		
		try {
			Query q = manager.createNamedQuery("Controller.getCommandFDs");
    		q.setParameter("idCommand", rCommand.getId());
    		list = q.getResultList();
    		
		}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
		
		return list;
	}
	
	/**
	 * Il metodo restituisce il comando relativo ad un ente che tratta uno o + FD
	 * e dato un determinato commandtype
	 */
	public List<RCommand> getRCommandsByFontiAndType(String belfiore,List<Long> fonti, Long idType) {

    	List<RCommand> retCommands = new ArrayList<RCommand>();
    	
    	try {
    		List<RCommand> listaCommand = new ArrayList<RCommand>();
    		
    		Query q = manager.createNamedQuery("Controller.getCommandsByFontiType");
    		q.setParameter("fonti", fonti);
    		q.setParameter("idCommandType", idType);
    		
    		listaCommand.addAll(q.getResultList());
    		
    		for(int i=0; i<listaCommand.size(); i++) {
    			RCommand rcmd = listaCommand.get(i);
    			
    			//verifica presenza customizzazioni del comando
    			RCommand rc = this.getON(rcmd.getId(), belfiore);
    			if(rc != null) {
    				//listaCommand.set(i, rc);
    				retCommands.add(rc);
    			}
    			else {
    				//verifica presenza esclusione del comando
    				rc = this.getOFF(rcmd.getId(), belfiore);
    				if(rc == null) {
    					retCommands.add(rcmd);
    				}
    			}
    		}
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
    	
    	return retCommands;
	}
	
	/**
	 * Il metodo restituisce il comando relativo ad un ente che non è riferito ad alcuna fonte
	 * e dato un determinato commandtype
	 */
	public List<RCommand> getRCommandsByTypeWithoutFonti(String belfiore, Long idType) {

    	List<RCommand> retCommands = new ArrayList<RCommand>();
    	
    	try {
    		List<RCommand> listaCommand = new ArrayList<RCommand>();
    		
    		Query q = manager.createNamedQuery("Controller.getCommandsByTypeWoFonti");
    		q.setParameter("idCommandType", idType);
    		
    		listaCommand.addAll(q.getResultList());
    		
    		for(int i=0; i<listaCommand.size(); i++) {
    			RCommand rcmd = listaCommand.get(i);
    			
    			//verifica presenza customizzazioni del comando
    			RCommand rc = this.getON(rcmd.getId(), belfiore);
    			if(rc != null) {
    				//listaCommand.set(i, rc);
    				retCommands.add(rc);
    			}
    			else {
    				//verifica presenza esclusione del comando
    				rc = this.getOFF(rcmd.getId(), belfiore);
    				if(rc == null) {
    					retCommands.add(rcmd);
    				}
    			}
    		}
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
    	
    	return retCommands;
	}
	
	/**
	 * Il metodo restituisce tutti i commandlaunch limitati nei risultati
	 */
	public List<RCommandLaunch> getRCommandLaunchByRange(List<String> enti, Integer start, Integer maxResult){
		    	
    	try {
    		//logger.debug("Recupero tutti i RCommandLaunch");
    		
    		Query q = manager.createNamedQuery("Controller.getAllCommandLaunch");
    		q.setParameter("enti", enti);
    		if(start != null)
    			q.setFirstResult(start);
    		if(maxResult != null)
    			q.setMaxResults(maxResult);
    		
    		return  q.getResultList();
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
	}
	
	/**
	 * Il metodo restituisce la count di tutti i commandlaunch
	 */
	public Long getRCommandLaunchCount(List<String> enti){
		    	
    	try {
    		//logger.debug("Recupero tutti i RCommandLaunch");
    		
    		Query q = manager.createNamedQuery("Controller.getAllCommandLaunchCount");
    		q.setParameter("enti", enti);
    		
    		return  (Long) q.getSingleResult();
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
	}
	
	/**
	 * Il metodo restituisce tutti i commandlaunch secondo un belfiore
	 */
	public List<RCommandLaunch> getRCommandLaunchByBelfiore(String belfiore, Integer start, Integer maxResult){
		    	
    	try {
    		//logger.debug("Recupero tutti i RCommandLaunch");
    		
    		Query q = manager.createNamedQuery("Controller.getCommandLaunchByBelfiore");
    		q.setParameter("belfiore", belfiore);
    		if(start != null)
    			q.setFirstResult(start);
    		if(maxResult != null)
    			q.setMaxResults(maxResult);
    		
    		return  q.getResultList();    			
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
	}
	
	/**
	 * Il metodo restituisce la count di tutti i commandlaunch secondo il belfiore
	 */
	public Long getRCommandLaunchCountByBelfiore(String belfiore){
		    	
    	try {
    		//logger.debug("Recupero tutti i RCommandLaunch");
    		
    		Query q = manager.createNamedQuery("Controller.getCommandLaunchCountByBelfiore");
    		q.setParameter("belfiore", belfiore);
    		return  (Long) q.getSingleResult();
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
	}
	
	/**
	 * Il metodo restituisce i commandlaunch secondo un idCommand
	 */
	public List<RCommandLaunch> getRCommandLaunch(Long idCommand, String belfiore){
		
    	try {
    		//logger.debug("Recupero RCommandLaunch by idCommand:" + idCommand);
    		
    		Query q = manager.createNamedQuery("Controller.getCommandLaunch");
    		q.setParameter("command", idCommand);
    		q.setParameter("belfiore", belfiore);
    		return q.getResultList();
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
    	
	}
	
	/**
	 * Il metodo restituisce i commandlaunch secondo un idScheduler
	 */
	public List<RCommandLaunch> getRCommandLaunchByScheduler(Long idScheduler){
		
    	try {
    		//logger.debug("Recupero RCommandLaunch by idScheduler:" + idScheduler);
    		
    		Query q = manager.createNamedQuery("Controller.getCommandLaunchByScheduler");
    		q.setParameter("idScheduler", idScheduler);
    		return q.getResultList();
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
    	
	}
	
	/**
	 * Il metodo restituisce l'ultimo commandLaunch inserito
	 * secondo un certo tipo di command
	 */
	public RCommandLaunch getLastRCommandLaunch(Long idCommand,String belfiore) {
		
    	try {
    		logger.debug("Recupero ultimo commandLaunch [RCommandId: "+
    								idCommand + "]");
    		
    		Query q = manager.createNamedQuery("Controller.getCommandLaunch");
    		q.setParameter("command", idCommand);
    		q.setParameter("belfiore", belfiore);
    		
    		List<RCommandLaunch> rcl = q.getResultList();
    		if(rcl.size() > 0){
    			return rcl.get(0);
    		}else 
    			return null;
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
		
	}

	public RCommand getRCommand(Long idCommand) {
		RCommand rCommand = null;
    	
    	try {
    		Query q = manager.createNamedQuery("Controller.getCommandByID");
    		q.setParameter("idCommand", idCommand);
    		
    		rCommand = (RCommand)q.getSingleResult();
    	}catch(NoResultException nre) {
    		logger.warn("Nessun comando trovato [cod. command : "+idCommand+"]");
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage());
    		throw new ServiceException(t);
    	}
    	
    	return rCommand;
	}
	
	/**
	 * Il metodo restituisce tutti i commandAck di un commandLaunch
	 */
	public List<RCommandAck> getRCommandAck(Long idCommandLaunch){
		    	
    	try {
    		//logger.debug("Recupero tutti i RCommandAck by idCommandLaunch:" + idCommandLaunch);
    		
    		Query q = manager.createNamedQuery("Controller.getCommandAck");
    		q.setParameter("commandlaunch", idCommandLaunch);
    		
    		return q.getResultList();
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
	}

	public List<RCommand> getAllRCommands() {
		try {
    		
    		Query q = manager.createNamedQuery("Controller.getAllRCommands");
    		
    		return  q.getResultList();
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
	}


	public List<RCommand> getRCommandsByType(String belfiore, Long idType) {
		List<RCommand> retCommands = new ArrayList<RCommand>();
    	
    	try {
    		List<RCommand> listaCommand = new ArrayList<RCommand>();
    		
    		Query q = manager.createNamedQuery("Controller.getCommandsByType");
    		q.setParameter("idCommandType", idType);
    		
    		listaCommand.addAll(q.getResultList());
    		
    		for(int i=0; i<listaCommand.size(); i++) {
    			RCommand rcmd = listaCommand.get(i);
    			
    			//verifica presenza customizzazioni del comando
    			RCommand rc = this.getON(rcmd.getId(), belfiore);
    			if(rc != null) {
    				//listaCommand.set(i, rc);
    				retCommands.add(rc);
    			}
    			else {
    				//verifica presenza esclusione del comando
    				rc = this.getOFF(rcmd.getId(), belfiore);
    				if(rc == null) {
    					retCommands.add(rcmd);
    				}
    			}
    		}
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
    	
    	return retCommands;
	}

	public List<RCommand> getRCommandsByFontiAndType(int start, int maxrows,
			String belfiore, List<Long> fonti, Long idType) {

		List<RCommand> retCommands = new ArrayList<RCommand>();
    	
    	try {
    		List<RCommand> listaCommand = new ArrayList<RCommand>();
    		
    		Query q = manager.createNamedQuery("Controller.getCommandsByFontiType");
    		q.setFirstResult(start);
    		q.setMaxResults(maxrows);
    		q.setParameter("fonti", fonti);
    		q.setParameter("idCommandType", idType);
    		
    		listaCommand.addAll(q.getResultList());
    		
    		for(int i=0; i<listaCommand.size(); i++) {
    			RCommand rcmd = listaCommand.get(i);
    			
    			//verifica presenza customizzazioni del comando
    			RCommand rc = this.getON(rcmd.getId(), belfiore);
    			if(rc != null) {
    				//listaCommand.set(i, rc);
    				retCommands.add(rc);
    			}
    			else {
    				//verifica presenza esclusione del comando
    				rc = this.getOFF(rcmd.getId(), belfiore);
    				if(rc == null) {
    					retCommands.add(rcmd);
    				}
    			}
    		}
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
    	
    	return retCommands;
	}

	public Long getRCommandsByFontiAndTypeCount(String belfiore,List<Long> fonti, Long idType) {
		
		Long count = new Long(0);
		List<RCommand> retCommands = new ArrayList<RCommand>();
    	
    	try {
    		List<RCommand> listaCommand = new ArrayList<RCommand>();
    		
    		Query q = manager.createNamedQuery("Controller.getCommandsByFontiType");
    		q.setParameter("fonti", fonti);
    		q.setParameter("idCommandType", idType);
    		
    		listaCommand.addAll(q.getResultList());
    		
    		for(int i=0; i<listaCommand.size(); i++) {
    			RCommand rcmd = listaCommand.get(i);
    			
    			//verifica presenza customizzazioni del comando
    			RCommand rc = this.getON(rcmd.getId(), belfiore);
    			if(rc != null) {
    				//listaCommand.set(i, rc);
    				retCommands.add(rc);
    			}
    			else {
    				//verifica presenza esclusione del comando
    				rc = this.getOFF(rcmd.getId(), belfiore);
    				if(rc == null) {
    					retCommands.add(rcmd);
    				}
    			}
    		}
    		
    		count = new Long(retCommands.size());
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
    	
    	return count;
	}
	
	public Long getRCommandsByFonteTypesCount(String belfiore, Long fonte, List<Long> types) {
		
		Long count = new Long(0);
		List<RCommand> retCommands = new ArrayList<RCommand>();
    	
    	try {
    		List<RCommand> listaCommand = new ArrayList<RCommand>();
    		
    		Query q = manager.createNamedQuery("Controller.getCommandsByFonteTypes");
    		q.setParameter("fonte", fonte);
    		q.setParameter("idCommandTypes", types);
    		
    		listaCommand.addAll(q.getResultList());
    		
    		for(int i=0; i<listaCommand.size(); i++) {
    			RCommand rcmd = listaCommand.get(i);
    			
    			//verifica presenza customizzazioni del comando
    			RCommand rc = this.getON(rcmd.getId(), belfiore);
    			if(rc != null) {
    				//listaCommand.set(i, rc);
    				retCommands.add(rc);
    			}
    			else {
    				//verifica presenza esclusione del comando
    				rc = this.getOFF(rcmd.getId(), belfiore);
    				if(rc == null) {
    					retCommands.add(rcmd);
    				}
    			}
    		}
    		
    		count = new Long(retCommands.size());
    		
    	}catch(Throwable t) {
    		logger.error("Eccezione: "+t.getMessage(),t);
    		throw new ServiceException(t);
    	}
    	
    	return count;
	}
	
	public List<Object[]> getRCommandLaunchJoinStatiAck(Long idCommand, String belfiore, String mese, String anno) {
		
		String sql = "SELECT CL.*, "
				+ "TS.ID_STATO, "
				+ "RAS.ID AS ID_1, RAS.DESCR, RAS.TIPO, "
				+ "RT.ID AS ID_2, RT.DESCR AS DESCR_1, "
				+ "CA.ID AS ID_3, CA.MESSAGE, CA.FK_COMMAND_LAUNCH, CA.LOG_DATE, CA.ACK_NAME, "
				+ "RC.ID AS ID_4, RC.DESCR AS DESCR_2, RC.SYSTEM_COMMAND, RC.LONG_DESCR, RC.COD_COMMAND, "
				+ "RT1.ID AS ID_5, RT1.DESCR AS DESCR_3 "
				+ "FROM R_COMMAND_LAUNCH CL, R_TRACCIA_STATI TS, R_ANAG_STATI RAS, R_COMMAND_TYPE RT, R_COMMAND_ACK CA, R_COMMAND RC, R_COMMAND_TYPE RT1 "
				+ "WHERE CL.FK_COMMAND = ? "
				+ "AND CL.BELFIORE = ? "
				+ "AND (TO_CHAR(CL.DATE_START, 'MMYYYY') = NVL(?, TO_CHAR(CL.DATE_START, 'MM')) || NVL(?, TO_CHAR(CL.DATE_START, 'YYYY')) "
				+ "OR TO_CHAR(CL.DATE_END, 'MMYYYY') = NVL(?, TO_CHAR(CL.DATE_END, 'MM')) || NVL(?, TO_CHAR(CL.DATE_END, 'YYYY'))) "
				+ "AND CL.PROCESSID = TS.PROCESSID "
				+ "AND TS.ID_STATO = RAS.ID "
				+ "AND RAS.FK_COMMAND_TYPE = RT.ID "
				+ "AND CA.FK_COMMAND_LAUNCH = CL.ID "
				+ "AND CA.FK_COMMAND = RC.ID "
				+ "AND RC.FK_COMMAND_TYPE = RT1.ID "
				+ "ORDER BY CL.DATE_START DESC, CA.ID";
		
		Query q = manager.createNativeQuery(sql);
		q.setParameter(1, idCommand);
		q.setParameter(2, belfiore);
		q.setParameter(3, mese);
		q.setParameter(4, anno);
		q.setParameter(5, mese);
		q.setParameter(6, anno);
		return q.getResultList();	
	}

    
}
