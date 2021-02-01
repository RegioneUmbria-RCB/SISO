package it.webred.rulengine.brick.reperimento.impl;

import java.io.File;
import java.util.Properties;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.brick.bean.WarningAck;
import it.webred.rulengine.brick.reperimento.AbstractReperimentoCommand;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class ReperimentoLocalImpl extends AbstractReperimentoCommand {

	//nome della proprietà di configurazione
	public static final String DIR_FILE_KEY = "dir.files";
	
	public ReperimentoLocalImpl(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandAck doReperimento(String belfiore, Long idFonte, String reProcessId,  Context ctx) throws CommandException {
		log.debug("Reperimento FD locale");
		
		CommandAck ack = null;
		
		//recupero del path locale dell ente/fd
		ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		AmKeyValueExt param = cdm.getAmKeyValueExtByKeyFonteComune(DIR_FILE_KEY, belfiore, idFonte.toString());
		log.debug("Directory reperimento file: "+param.getValueConf());
		
		//controllo presenza fd
		File f = new File(param.getValueConf());
		if(f.exists()) {
			if(f.isDirectory()) {
				File[] ff = f.listFiles();
				if(ff.length == 0) {
					ack = new NotFoundAck("La directory non contiene file di fonte dati");
				}
				else {
					//controllo se tra gli oggetti file elencati c'è almeno un file di fonte dati
					boolean fdExist = false;
					for(File currF: ff) {
						if(currF.isFile()) {
							fdExist = true;
							break;
						}
					}
					
					if(fdExist) {
						ack = new ApplicationAck("Fonte dati presente");
					}
					else {
						ack = new NotFoundAck("La directory non contiene file di fonte dati");
					}
				}
			}
			else {
				ack = new ErrorAck(DIR_FILE_KEY+" non è una directory valida");
			}
		}
		else {
			ack = new ErrorAck("Directory non esiste");
		}
				
		return ack;
	}

}
