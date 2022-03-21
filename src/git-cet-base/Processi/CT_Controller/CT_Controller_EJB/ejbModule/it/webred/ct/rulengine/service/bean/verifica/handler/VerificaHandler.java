package it.webred.ct.rulengine.service.bean.verifica.handler;



import it.webred.ct.rulengine.service.bean.verifica.handler.dto.VerificaHandlerInfo;
import it.webred.ct.rulengine.service.bean.verifica.handler.dto.VerificaHandlerParams;
import it.webred.ct.rulengine.service.exception.ServiceException;


public interface VerificaHandler {
	
	/**
	 * Verifica che altri porcessi non stiano occupando la FD
	 * @param p1
	 * @return
	 * @throws ServiceException
	 */
	public VerificaHandlerInfo verificaFDLocked(VerificaHandlerParams p1) throws ServiceException;
	
	/**
	 * Verifca che eventuali processi precedenti sulla FD siano terminato con un esito adatto
	 * a svolgere il processo chiamato
	 * @param p1
	 * @return
	 * @throws ServiceException
	 */
	public VerificaHandlerInfo verificaPrevFDProcess(VerificaHandlerParams p1) throws ServiceException;
	
	/**
	 * Verifica che il processo chiamato non sia già in esecuzione
	 * @param p1
	 * @return
	 * @throws ServiceException
	 */
	public VerificaHandlerInfo verificaFDStatoCorrente(VerificaHandlerParams p1) throws ServiceException;
	
	
}
