package it.webred.rulengine.brick.dummy;

import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class DummyRule extends Command implements Rule {
	
	private static Logger log = Logger.getLogger(DummyRule.class.getName());
	
	public DummyRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx1) throws CommandException {
		log.info("[DUMMY] Comando generico eseguito !!");
		return new ApplicationAck("Comando DUMMY generico eseguito");
	}

}
