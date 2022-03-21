package it.webred.ct.rulengine.service.bean.verifica.handler;

import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.service.bean.verifica.handler.dto.VerificaHandlerInfo;
import it.webred.ct.rulengine.service.bean.verifica.handler.dto.VerificaHandlerParams;
import it.webred.ct.rulengine.service.exception.ServiceException;

import java.util.List;

import org.apache.log4j.Logger;

public abstract class AbstractVerificaHandler implements VerificaHandler {
	
	protected Logger logger = Logger.getLogger("VerificaHandler_log");
	
	
	protected static final Long REPERIMENTO = new Long(10);
	protected static final Long ACQUISIZIONE = new Long(20);
	protected static final Long D_CONTROLLO = new Long(31);
	protected static final Long D_CONFRONTO = new Long(32);
	protected static final Long CORRELAZIONE = new Long(33);
	protected static final Long ELABORAZIONE = new Long(34);
	protected static final Long ACCERTAMENTO = new Long(35);
	protected static final Long STATISTICA = new Long(36);
	protected static final Long RICERCA = new Long(37);
	
	/**
	 * Implementazione unica per tutte le classi verifica handler 
	 * per il controllo del lock sulle FD
	 * 
	 * @param listFDOnlineProcess
	 * @return
	 * @throws ServiceException
	 */
	protected VerificaHandlerInfo isLocked(List<RProcessMonitor> listFDOnlineProcess) throws ServiceException {
		VerificaHandlerInfo vhi = new VerificaHandlerInfo(true,"");
		if(listFDOnlineProcess.size() > 0) {
			vhi.setEsito(false);
			StringBuilder sb = new StringBuilder("Fonte Dati già occupata dai seguenti processi: ");
			
			for(RProcessMonitor item: listFDOnlineProcess) {
				sb.append(item.getProcessid());
				sb.append(" | ");
			}
			
			vhi.setMessage(sb.toString()+" [I]");
		}
		
		return vhi;
	}
	
	
	public abstract VerificaHandlerInfo verificaFDLocked(VerificaHandlerParams p1) throws ServiceException;
	
	public abstract VerificaHandlerInfo verificaPrevFDProcess(VerificaHandlerParams p1) throws ServiceException;
	
	public abstract VerificaHandlerInfo verificaFDStatoCorrente(VerificaHandlerParams p1) throws ServiceException;
	
}
