package it.webred.rulengine.brick.test;

import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class PrintString extends Command implements Rule {
	
	private static final Logger log = Logger.getLogger(PrintString.class.getName());

	

	public PrintString(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}



	@Override
	public CommandAck run(Context ctxRE) throws CommandException {
		CommandAck cak = null;
		
		try {
			String param = (String) ctxRE.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			log.debug("Stringa upperata esito: "+param);
						
			cak = new ApplicationAck("PRINT OK");

		}catch(Exception e) {
			log.debug("Eccezione: "+e.getMessage(),e);
			cak = new ErrorAck("PRINT KO");
		}
		
		return cak;
	}
	
	
	

}
