package it.webred.rulengine.brick.reperimento;

import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RunningAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public abstract class AbstractReperimentoCommand extends Command {
	
	protected Logger log = Logger.getLogger(this.getClass().getName());
	
	
	public AbstractReperimentoCommand(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck run(Context ctx1) throws CommandException {
		CommandAck ack = null;
		
		try {
			String belfiore = ctx1.getBelfiore();
			String processId = ctx1.getProcessID();
			Long idFonte = ctx1.getIdFonte();
			
			log.debug("Chiamata per reperimento ["+belfiore+"|"+idFonte+"|"+processId+"]");
			ack = doReperimento(belfiore,idFonte,processId, ctx1);
			log.debug("Chiamata effettuata");
			
		}catch(CommandException ce) {
			log.error("Eccezione reperimento: "+ce.getMessage());
			ack = new ErrorAck(ce.getMessage());
		}
		
		return ack;
	}
	
	
	public abstract CommandAck doReperimento(String belfiore,Long idFonte,String reProcessId, Context ctx) throws CommandException;
}
