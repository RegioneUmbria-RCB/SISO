package it.webred.ct.rulengine.service.bean.verifica.handler.impl;

import java.util.List;

import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.service.bean.verifica.handler.AbstractVerificaHandler;
import it.webred.ct.rulengine.service.bean.verifica.handler.dto.VerificaHandlerInfo;
import it.webred.ct.rulengine.service.bean.verifica.handler.dto.VerificaHandlerParams;
import it.webred.ct.rulengine.service.exception.ServiceException;

public class VerificaAcquisizioneHandler extends AbstractVerificaHandler {

	@Override
	public VerificaHandlerInfo verificaFDLocked(VerificaHandlerParams p1)
			throws ServiceException {
		
		return isLocked(p1.getListaRProcessMonitor());
	}

	@Override
	public VerificaHandlerInfo verificaPrevFDProcess(VerificaHandlerParams p1) throws ServiceException {
		
		List<RProcessMonitor> prevs = p1.getPrevRProcessMonitor();
		VerificaHandlerInfo vhi = new VerificaHandlerInfo(true, "");
		if(prevs != null && prevs.size() > 0)
			vhi = new VerificaHandlerInfo(true, "[F]");
		
		try {
			if(prevs != null) {
				for(RProcessMonitor prev: prevs) {
					
					if(prev.getRCommand().getRCommandType().getId() == super.ACQUISIZIONE) {
						//se si tratta dello stesso tipo di comando nn va considerato in questo tipo di controllo
						continue;
					}
					
					RAnagStati statoFD = prev.getRAnagStato();
					vhi.setMessage("["+statoFD.getTipo()+"]");
					
					switch(statoFD.getId().intValue()) {
						
						/*
						case 17 : // TCONTR err
						case 18 : //TCONFR err
						case 19 :  //TCORREL err
						case 20 : { //TELAB err
							vhi.setEsito(false);
							vhi.setMessage("Lo stato attuale della fonte dati è "+statoFD.getDescr().toUpperCase()+
									" ["+statoFD.getTipo()+"]");
							break;
						}
						*/
					
						case 207 : { //fd non disponibile							
							vhi.setEsito(true);
							vhi.setMessage("Lo stato attuale della fonte dati è "+statoFD.getDescr().toUpperCase()+ 
										   " ["+statoFD.getTipo()+"]");
							break;
						}
						
						case 2 : {
							//esclusivamente per errore in reperimento fd
							vhi.setMessage("[F]");
							break;
						}
						default: {  //1,2,4,7, ecc....
							//acquisizione può essere eseguito
							break;
						}
					}
					
					//uscita forzata in quanto viene considerato solo il primop proceesso in lista
					break;
				}
			}
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return vhi;
	}

	@Override
	public VerificaHandlerInfo verificaFDStatoCorrente(VerificaHandlerParams p1) throws ServiceException {
		
		VerificaHandlerInfo vhi = new VerificaHandlerInfo(true,"[F]");
		
		try {
			RProcessMonitor rpm = p1.getrProcessMonitor();
			if(rpm != null) {
				vhi.setMessage("["+rpm.getRAnagStato().getTipo()+"]");
				switch(rpm.getRAnagStato().getId().intValue()) {
					case 4 : {
						//acquisizione non può essere eseguito
						vhi.setEsito(false);
						vhi.setMessage("Il processo chiamato è già presente con stato "+rpm.getRAnagStato().getDescr().toUpperCase()+
								" ["+rpm.getRAnagStato().getTipo()+"]");
						break;
					}
					default: {
						//do nothing
					}
				}
			}			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return vhi;
	}

}
