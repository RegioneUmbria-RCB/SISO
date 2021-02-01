/*
 * Created on 25-ott-2005
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package it.webred.rulengine.impl;

import it.webred.rulengine.Command;
import it.webred.rulengine.db.dao.IRCommandDAO;
import it.webred.rulengine.db.dao.IRRuleDAO;
import it.webred.rulengine.db.dao.impl.RCommandDAOImpl;
import it.webred.rulengine.db.dao.impl.RRuleDAOImpl;
import it.webred.rulengine.db.model.RCommand;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import org.apache.log4j.Logger;



/**
 * La ComamndFactory è una classe che serve a fornire un Comamnd
 * 
 */
public class CommandFactory {
	
	private static final Logger log = Logger.getLogger(CommandFactory.class.getName());
	
	private static IRCommandDAO cDao = null;
	private static IRRuleDAO rDao = null;
	
	
	static {
		cDao = new RCommandDAOImpl();
		rDao = new RRuleDAOImpl();
	}
	
	public static Command getCommand(Integer idCommand, String codCommand , boolean evenSystemCommand)
		throws CommandException
	{
		Command cmd = null;
		RCommand element = null;
		
		try
		{
			if (idCommand != null) {				
				element = cDao.getRCommand(idCommand.longValue());
				
			} else {
				element = cDao.getRCommandByCodCommand(codCommand);
			}

			if (element != null) {

				String cmdName=null;
				
				if (evenSystemCommand || (!evenSystemCommand && element.getSystemCommand().intValue() == 0)) {
					BeanCommand bean = new BeanCommand(element);
					
					cmd = getCommand(bean,evenSystemCommand);
					
					/*
					List<RRule> llRules = rDao.getListaRRule(element.getId().longValue());
					cmdName = ((RRule) llRules.toArray()[0]).getClassName();
					Class cl = Class.forName(cmdName);
					Constructor cnst = cl.getConstructor(new Class[]
					                                           { BeanCommand.class });
					cmd = (Command) cnst.newInstance(new Object[]
					{ bean });
					*/
				}
				else {
					throw new CommandException("Command inesistente:" + cmdName);
				}
			}
			/*
			else
			{
				if (idCommand!=null) {
					element = cDao.getRCommand2(idCommand.longValue());
					
				} else {
					element = cDao.getRCommandByCodCommand2(codCommand);
				}
				
				if (element != null)
				{
					if (evenSystemCommand || (!evenSystemCommand && element.getSystemCommand().intValue() == 0))
					{
						BeanCommand bean = new BeanCommand(element);
						Class cl = Class.forName("it.webred.rulengine.impl.ChainBase");
						Constructor cnst = cl.getConstructor(new Class[]
						{ BeanCommand.class });
						cmd = (Command) cnst.newInstance(new Object[]
						{ bean });
					}
					else {
						throw new CommandException("Command inesistente:ChainBase");
					}
				}
			}
			*/
			
			return cmd;

		}
		catch (Exception e)
		{
			throw new CommandException(e);
		}
	}

	public static Command getCommand(String name, boolean evenSystemCommand)
		throws CommandException
	{
		Command cmd = null;

		try {			
			RCommand element = cDao.getRCommandByRRuleClassname(name);
			
			Integer id = element.getId();
			cmd = getCommand(id, null, evenSystemCommand);
			
			if (cmd == null)
				throw new CommandException("Command inesistente id:"+id);
			return cmd;

		}
		catch (Exception e)
		{
			throw new CommandException(e);
		}
	}

	public static Command getCommand(BeanCommand bc, boolean evenSystemCommand)
		throws CommandException
	{
		Command cmd = null;
		
		try
		{
			log.debug("Nome regola da caricare: "+bc.getRCommand().getCodCommand());
			String jrulecfg = "/config/RULES/"+bc.getRCommand().getCodCommand().toLowerCase()+".properties";
			Properties p = _getConfigReference(jrulecfg);
			log.debug("File di configurazione della regola recuperato");
			
			Class cl = Class.forName(p.getProperty("rengine.rule.class"));
			Constructor cnst = cl.getConstructor(new Class[]
			                         { BeanCommand.class, Properties.class });
			
			cmd = (Command) cnst.newInstance(new Object[] { bc, p });	
			
			
			/*
			if (bc.isRule())
			{
				List<RRule> llRules = rDao.getListaRRule(bc.getRCommand().getId().longValue());
				Class cl = Class.forName(((RRule) llRules.toArray()[0]).getClassName());
				Constructor cnst = cl.getConstructor(new Class[]
				{ BeanCommand.class });
				cmd = (Command) cnst.newInstance(new Object[]
				{ bc });
			}
			
			else
			{
				Class cl = Class.forName("it.webred.rulengine.impl.ChainBase");
				Constructor cnst = cl.getConstructor(new Class[]
				{ BeanCommand.class });
				cmd = (Command) cnst.newInstance(new Object[]
				{ bc });
			}
			*/
			
			return cmd;
		}
		catch (Exception e)
		{
			throw new CommandException(e);
		}
		finally
		{
		}

	}
	

	
	/**
	 * Recupero file cfg 
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static Properties _getConfigReference(String path) throws Exception {
		InputStream is = BaseCommandFactory.class.getResourceAsStream(path);
		Properties p = new Properties();
		p.load(is);
		return p;
	}

}
