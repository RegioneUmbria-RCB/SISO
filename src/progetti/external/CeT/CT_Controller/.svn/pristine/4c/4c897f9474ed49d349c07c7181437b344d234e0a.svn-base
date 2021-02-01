package it.webred.ct.rulengine.service.bean.verifica.handler.impl;

import java.util.List;

import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.service.bean.verifica.handler.AbstractVerificaHandler;
import it.webred.ct.rulengine.service.bean.verifica.handler.dto.VerificaHandlerInfo;
import it.webred.ct.rulengine.service.bean.verifica.handler.dto.VerificaHandlerParams;
import it.webred.ct.rulengine.service.exception.ServiceException;

public class VerificaTElaborazioneHandler extends AbstractVerificaHandler {

	@Override
	public VerificaHandlerInfo verificaFDLocked(VerificaHandlerParams p1)
			throws ServiceException {

		return isLocked(p1.getListaRProcessMonitor());
	}

	@Override
	public VerificaHandlerInfo verificaFDStatoCorrente(VerificaHandlerParams p1)
			throws ServiceException {

		List<RProcessMonitor> prevs = p1.getPrevRProcessMonitor();
		VerificaHandlerInfo vhi = new VerificaHandlerInfo(true, "");
		if(prevs != null && prevs.size() > 0)
			vhi = new VerificaHandlerInfo(true, "[F]");

		try {
			if (prevs != null) {
				for (RProcessMonitor prev : prevs) {

					if (prev.getRCommand().getRCommandType().getId() == super.ELABORAZIONE) {
						// se si tratta dello stesso tipo di comando nn va
						// considerato in questo tipo di controllo
						continue;
					}

					RAnagStati statoFD = prev.getRAnagStato();
					vhi.setMessage("[" + statoFD.getTipo() + "]");

					// USCITA FORZATA, va controllato solo il primo record che
					// equivale
					// al processo precedente eseguito sulla FD
					break;
				}
			}
		} catch (Throwable t) {
			logger.error("Eccezione: " + t.getMessage());
			throw new ServiceException(t);
		}

		return vhi;
	}

	@Override
	public VerificaHandlerInfo verificaPrevFDProcess(VerificaHandlerParams p1)
			throws ServiceException {

		VerificaHandlerInfo vhi = new VerificaHandlerInfo(true, "[F]");

		try {
			RProcessMonitor rpm = p1.getrProcessMonitor();
			if (rpm != null) {
				vhi.setMessage("[" + rpm.getRAnagStato().getTipo() + "]");
				switch (rpm.getRAnagStato().getId().intValue()) {
				case 10: {
					// elaborazione non può essere eseguito
					vhi.setEsito(false);
					vhi.setMessage("Il processo chiamato è già presente con stato "
							+ rpm.getRAnagStato().getDescr().toUpperCase()
							+ " [" + rpm.getRAnagStato().getTipo() + "]");
					break;
				}
				default: {
					// do nothing
				}
				}
			}
		} catch (Throwable t) {
			logger.error("Eccezione: " + t.getMessage());
			throw new ServiceException(t);
		}

		return vhi;
	}

}
