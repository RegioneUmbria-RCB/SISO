package it.webred.rulengine.impl.factory;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.db.dao.IRCommandAckDAO;
import it.webred.rulengine.db.dao.IRCommandDAO;
import it.webred.rulengine.db.dao.IRCommandLaunchDAO;
import it.webred.rulengine.db.dao.impl.RCommandAckDAOImpl;
import it.webred.rulengine.db.dao.impl.RCommandDAOImpl;
import it.webred.rulengine.db.dao.impl.RCommandLaunchDAOImpl;
import it.webred.rulengine.db.model.RCommand;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.BaseCommandFactory;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.Variable;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * Classe factory per le sole regole di catene jelly
 * 
 * @author webred
 *
 */
public class RCommandFactory extends BaseCommandFactory {
	
	private static final Logger log = Logger.getLogger(RCommandFactory.class.getName());
	
	
	
	public RCommandFactory() {
		super();		
	}


	public Command getCommand(String codCommand, boolean evenSystemCommand)	throws CommandFactoryException {
		
		Command cmd = null;
		
		try {
			//in base al nome recuperare il properties e chiamare il metodo getCommand sotto
			String jrulecfg = "/config/RULES/"+codCommand.toLowerCase()+".properties";
			Properties p = this.getConfigReference(jrulecfg);
			
			cmd = this.getCommand(p, evenSystemCommand);
			
		}catch(Exception e) {
			log.error("Problemi nel recupero file di cfg della regola "+codCommand);
			throw new CommandFactoryException("Problemi nel recupero file di cfg della regola "+codCommand, e);
		}
		
		return cmd;
	}

	
	/**
	 * Properties objectConfig fa riferimento al file di configurazione della regola
	 * che fa parte della catena jelly
	 */
	@SuppressWarnings("unchecked")
	public Command getCommand(Properties objectConfig, boolean evenSystemCommand)
														throws CommandFactoryException {
		Command cmd = null;
		RCommand element = null;
		
		try {
			String classname = objectConfig.getProperty("rengine.rule.class");
			
			element = cDao.getRCommandByRRuleClassname(classname);
			
			if (element != null) {
				if (evenSystemCommand || 
						(!evenSystemCommand && element.getSystemCommand().intValue() == 0)) {
					
					BeanCommand bc = new BeanCommand(element);
					
					Class cl = Class.forName(classname);
					Constructor cnst = cl.getConstructor(new Class[]
					                         { BeanCommand.class, Properties.class });
					
					cmd = (Command) cnst.newInstance(new Object[] { bc, objectConfig });
					
				}
			}
			else {
				throw new CommandException("Commando regola di catena jelly inesistente:" + classname);
			}
			
		}catch(Exception e) {
			throw new CommandFactoryException("Errore recupero comando di catena Jelly", e);
		}
		
		return cmd;
	}
	
	
	
	
	/**
	 * Recupero file cfg 
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public Properties getConfigReference(String path) throws Exception {
		InputStream is = BaseCommandFactory.class.getResourceAsStream(path);
		Properties p = new Properties();
		p.load(is);
		return p;
	}


	@Override
	public Command getEventCommand(String codCommand, List<Variable> llvv)
			throws CommandFactoryException {
		// TODO Auto-generated method stub
		return null;
	}
}
