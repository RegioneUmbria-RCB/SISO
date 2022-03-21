package it.webred.rulengine.entrypoint.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RCommandLaunch;
import it.webred.rulengine.dwh.SitSintesiProcessiUtils;

import it.webred.rulengine.entrypoint.Launcher;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.BaseCommandFactory;
import it.webred.rulengine.impl.CommandUtil;
import it.webred.rulengine.impl.ContextBase;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.impl.factory.RCommandFactory;
import it.webred.rulengine.type.Variable;

/**
 * La classe implementa una tipologia di Launcher 
 * dedicata al lancio del singolo comando/regola
 * 
 * @author webred
 *
 */
public class CommandLauncher implements Launcher {
	private static final Logger log = Logger.getLogger(CommandLauncher.class.getName());
	
	private String belfiore;
	
	
	
	public CommandLauncher(String belfiore) {
		super();
		this.belfiore = belfiore;
	}


	public CommandAck executeCommand(String codCommand, String processId)
			throws CommandException {
		CommandAck cmAck = null;
		try {
			cmAck = this.executeCommand(codCommand, null, processId);
		}catch (Exception e) {
			throw new CommandException(e);
		}
		return cmAck;
	}

	public CommandAck executeCommand(String codCommand, HashMap map, String processId)
			throws CommandException {

		Context ctx = new ContextBase();
		Command cmd = null;
		CommandAck cmAck = null;
		
		try {
			ctx.setProcessID(processId);
			setMapToContext(map, ctx);
			
			//cmd = CommandFactory.getCommand(codCommand,true);
			RCommandFactory rf = (RCommandFactory)BaseCommandFactory.getFactory("R");
			cmd = rf.getCommand(codCommand, true);
			
			cmAck = cmd.execute(ctx);
			
			if (cmAck instanceof ErrorAck ) {
				log.debug("Effettuato il roolback applicativo del command codCommand=" + codCommand + "");
				rollBackConnection(ctx.getConnections());
				SitSintesiProcessiUtils.operazioneSincronizzata(processId, null, null, SitSintesiProcessiUtils.RIMUOVI_SINTESI_PROCESSO,this.belfiore);
			}
			else {
				SitSintesiProcessiUtils.operazioneSincronizzata( processId, null, null, SitSintesiProcessiUtils.SALVA_ULTIMI,this.belfiore);
				commitConnection(ctx.getConnections());
			}
			
		}catch (Exception e) {
			rollBackConnection(ctx.getConnections());
			log.error("Errore di lancio o chiusura comando -  roolback effettuato", e);
			RCommandLaunch rcl =  CommandUtil.getRCommandLaunch(processId);
			CommandAck bi = new ErrorAck("Errore durante il lancio o al termine dell'esecuzione - effettuato il roolback");
			CommandUtil.saveRCommandAck(rcl, bi, cmd.getBeanCommand() );
			
			try {
				SitSintesiProcessiUtils.operazioneSincronizzata( processId, null, null, SitSintesiProcessiUtils.RIMUOVI_SINTESI_PROCESSO,this.belfiore);
			}catch (Exception e1) {
				throw new CommandException(e);
			}
			
			throw new CommandException(e);
		}
			
		return cmAck;
	}

	public CommandAck executeCommand(String codCommand, HashMap map,
			String processId, List<Variable> variabili, String con) throws CommandException {
		
		Context ctx = new ContextBase();
		Command cmd = null;
		CommandAck cmAck = null;
		try {
			ctx.setProcessID(processId);
			setMapToContext(map, ctx);
			setVariablesToContext(variabili,ctx);
			
			//cmd = CommandFactory.getCommand(codCommand, true);
			RCommandFactory rf = (RCommandFactory)BaseCommandFactory.getFactory("R");
			cmd = rf.getCommand(codCommand, true);
			
			if (cmd instanceof DbCommand) {
				if (con != null) {
					((DbCommand)cmd).setConnectionName(con);
				} else {
					((DbCommand)cmd).setConnectionName("DWH_" + belfiore);
				}
			}
			
			cmAck = cmd.execute(ctx);
			if (cmAck instanceof ErrorAck ) {
				log.debug("Effettuato il roolback applicativo del command codCommand=" + codCommand + "");
				rollBackConnection(ctx.getConnections());
				SitSintesiProcessiUtils.operazioneSincronizzata(processId, null, null, SitSintesiProcessiUtils.RIMUOVI_SINTESI_PROCESSO,this.belfiore);
			}
			else {
				SitSintesiProcessiUtils.operazioneSincronizzata(processId, null, null, SitSintesiProcessiUtils.SALVA_ULTIMI,this.belfiore);
				commitConnection(ctx.getConnections());
			}
		}catch(Exception e) {
			rollBackConnection(ctx.getConnections());
			log.error("Errore di lancio o chiusura comando -  roolback effettuato", e);
			RCommandLaunch rcl =  CommandUtil.getRCommandLaunch(processId);
			CommandAck bi = new ErrorAck("Errore durante il lancio o al termine dell'esecuzione - effettuato il roolback");
			CommandUtil.saveRCommandAck(rcl, bi, cmd.getBeanCommand() );
			
			try {
				SitSintesiProcessiUtils.operazioneSincronizzata(processId, null, null, SitSintesiProcessiUtils.RIMUOVI_SINTESI_PROCESSO,this.belfiore);
			} catch (Exception e1) {
				throw new CommandException(e);
			}
			
			throw new CommandException(e);
		}
		
		return cmAck;
	}

	public CommandAck executeCommand(BeanCommand bc, HashMap map, String processId)
			throws CommandException {
		//TODO: do nothing
		return null;
	}
	
	
	
	public void runCommand(String codCommand, HashMap map, String connectionName, Context ctxOrigin, Connection conn )
	throws CommandException {
		
		if (conn==null) {
			throw new CommandException("runCommand : connessione obbligatoria!");	
		}
		
		Context ctx = new ContextBase();
		Command cmd = null;
		CommandAck cmAck = null;
		try {
			ctx.copiaAttributi(ctxOrigin);
			setMapToContext(map, ctx);
			
			//cmd = CommandFactory.getCommand(codCommand,true);
			RCommandFactory rf = (RCommandFactory)BaseCommandFactory.getFactory("R");
			cmd = rf.getCommand(codCommand, true);
			
			if (cmd instanceof DbCommand && connectionName!=null) {
				((DbCommand)cmd).setConnectionName(connectionName);
			}
			
			cmAck =  ((DbCommand)cmd).runWithConnection(ctx, conn);
			
			if(cmAck instanceof RejectAck ) {
				log.warn("Esecuzione comando rifiutata " + codCommand + " " + cmAck.getMessage());
				throw new CommandException(cmAck.getMessage());	
			}
			
			if (cmAck instanceof ErrorAck ) {
				log.warn("Errore nel lancio del command " + codCommand + "" + cmAck.getMessage());
				throw new CommandException(cmAck.getMessage());
			}
			
		}catch(CommandException e) {
			if (cmAck instanceof RejectAck)
				log.warn("Errore durante il lancio o al termine dell'esecuzione codCommand=" + codCommand);
			else
				log.error("Errore durante il lancio o al termine dell'esecuzione codCommand=" + codCommand, e);
				
			throw new CommandException( e.getMessage());
		}
		catch(Exception e) {
			log.error("Errore durante il lancio o al termine dell'esecuzione codCommand=" + codCommand,e);
			throw new CommandException( e.getMessage());
		}
	}
	
	
	
	
	private void setVariablesToContext(List<Variable> vars, Context ctx)	{
		if (vars != null && vars.size() > 0)
		{
			Iterator it = vars.iterator();
			while (it.hasNext())
			{
				Variable key = (Variable) it.next();
				ctx.addDeclarativeType(key.getName(), key);
			}
		}

	}
	
	@SuppressWarnings("unchecked")
	private void setMapToContext(HashMap map, Context ctx)
	{
		if (map != null && map.size() > 0)
			ctx.putAll(map);
			//risolto con putall
			/*
			 		{
			Set keys = map.keySet();
			Iterator it = keys.iterator();
			while (it.hasNext())
			{
				Object key = (Object) it.next();
				ctx.put(key, map.get(key));
			}
		}
			 */

	}
	
	private void commitConnection(HashMap<String, Connection> hm)
	{
		try
		{
			if(hm !=null && hm.size()>0)
			{
				for (Map.Entry<String, Connection> itemConnection : hm.entrySet())
				{
					Connection conn = itemConnection.getValue();
					try
					{
						conn.commit();
						conn.close();
					}
					catch (SQLException e1)
					{
						log.error(e1);
					}
				}
			}
		}
		catch(Exception e)
		{
			log.error(e);
		}
	}

	private void rollBackConnection(HashMap<String, Connection> hm)
	{
		try
		{
			if(hm !=null && hm.size()>0)
			{

				for (Map.Entry<String, Connection> itemConnection : hm.entrySet())
				{
					Connection conn = itemConnection.getValue();
					try
					{
						conn.rollback();
						conn.close();
					}
					catch (SQLException e1)
					{
						log.error(e1);
					}
				}
			}

		}
		catch(Exception e)
		{
			log.error(e);
		}
		
			
	}	
	
}
