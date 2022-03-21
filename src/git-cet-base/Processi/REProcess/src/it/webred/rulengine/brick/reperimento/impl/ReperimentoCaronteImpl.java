package it.webred.rulengine.brick.reperimento.impl;

import java.util.Properties;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.RunningAck;
import it.webred.rulengine.brick.reperimento.AbstractReperimentoCommand;
import it.webred.rulengine.brick.reperimento.executor.ListaProcessiExecutor;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class ReperimentoCaronteImpl extends AbstractReperimentoCommand implements Rule {

	public ReperimentoCaronteImpl(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandAck doReperimento(String belfiore, Long idFonte, String reProcessId, Context ctx) throws CommandException  {
		log.debug("Reperimento FD da Caronte");
		
		//lancio del thread
		try {
			new ListaProcessiExecutor(belfiore,idFonte,reProcessId).exec();
		} catch (Exception e) {
			throw new CommandException(e);

		}
		
		//return new RunningAck("Reprimento da Caronte in esecuzione");
		
		return new ApplicationAck("Reperimento da caronte eseguito");
		
	}

	

}
