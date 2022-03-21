package it.webred.rulengine.impl.bean;

import java.util.List;

import it.webred.rulengine.db.dao.impl.RRuleDAOImpl;
import it.webred.rulengine.db.model.RCommand;
import it.webred.rulengine.db.model.RRule;

/**
 * Bean che viene utilizzato per i comamnd sia che siano regole che catene.
 * 
 * @author sisani
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:24 $
 */
public class BeanCommand
{
	protected RCommand rCommand;

	/**
	 * 
	 */
	public BeanCommand()
	{
	}

	public BeanCommand(RCommand rc)
	{
		this.rCommand = rc;

	}

	/**
	 * Metodo per capire su il command è una regola(Rule o DbRule) o se si tratta 
	 * di una catena.
	 * 
	 * @return boolean - true se è una regola, false se non lo è
	 * @throws Exception 
	 */
	
	
	public boolean isRule() throws Exception
	{
		List<RRule> rulesCurrent = 
			(new RRuleDAOImpl()).getListaRRule(
					this.rCommand.getId().longValue());
		
		if (rulesCurrent != null &&	!rulesCurrent.isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

	/**
	 * @return
	 */
	public RCommand getRCommand()
	{
		return rCommand;
	}

	/**
	 * @param rc
	 */
	public void setRCommand(RCommand rc)
	{
		this.rCommand = rc;
	}

}
