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
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.model.RCommandLaunch;
import it.webred.rulengine.dto.ConfigurazioneEnte;
import it.webred.rulengine.dwh.SitSintesiProcessiUtils;
import it.webred.rulengine.entrypoint.Launcher;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.BaseCommandFactory;

import it.webred.rulengine.impl.CommandUtil;
import it.webred.rulengine.impl.ContextBase;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.impl.factory.JCommandFactory;

import it.webred.rulengine.type.Variable;

public class JellyLauncher implements Launcher {
	
	private static final Logger log = Logger.getLogger(JellyLauncher.class.getName());
	
	private String belfiore;
	private Long idFonte;
	private ConfigurazioneEnte configurazioneEnte;
	
	
	/**
	 * Costruttore
	 * 
	 * @param belfiore
	 * @param idFonte
	 */
	public JellyLauncher(String belfiore,Long idFonte,ConfigurazioneEnte configurazioneEnte) {
		super();
		this.belfiore = belfiore;
		this.idFonte = idFonte;
		this.configurazioneEnte = configurazioneEnte;
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
			ctx.setBelfiore(this.belfiore);
			ctx.setIdFonte(this.idFonte);
			ctx.setParametriConfigurazioneEnte(this.configurazioneEnte.getEnteProperties());
			ctx.setFonteDatiEnte(this.configurazioneEnte.getEnteFonteDati());
			ctx.setIdSched(this.configurazioneEnte.getIdSched());
			
			//setMapToContext(map, ctx);
						
			JCommandFactory jf = (JCommandFactory)BaseCommandFactory.getFactory("J");
			
			if(map != null) {
				//sono stati impostati manualemnte i params da passare a jelly
				ctx.setJellyParams(map);
				cmd = jf.getCommand(codCommand, false);	
			}
			else {
				//nessun parametro impostato manualmente da passare a jelly
				cmd = jf.getCommand(codCommand, true);
			}
			
			
			cmAck = cmd.execute(ctx);
			
			if (cmAck instanceof ErrorAck ) {
				log.info("Effettuato il roolback applicativo del command idCommand=" + codCommand + "");
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

	/**
	 * Implementazione del metodo riservata solo all'esecuzione dei comandi jelly 
	 * provenienti esclusivamente dalla gestione ad eventi 
	 */
	public CommandAck executeCommand(String codCommand, HashMap map,
									 String processId, List<Variable> variabili,
									 String con)  throws CommandException {

		Context ctx = new ContextBase();
		Command cmd = null;
		CommandAck cmAck = null;
		
		try {
			ctx.setProcessID(processId);
			ctx.setBelfiore(this.belfiore);
			ctx.setIdFonte(this.idFonte);
			ctx.setParametriConfigurazioneEnte(this.configurazioneEnte.getEnteProperties());
			ctx.setFonteDatiEnte(this.configurazioneEnte.getEnteFonteDati());
			ctx.setIdSched(this.configurazioneEnte.getIdSched());
						
			JCommandFactory jf = (JCommandFactory)BaseCommandFactory.getFactory("J");

			cmd = jf.getEventCommand(codCommand, variabili);
			cmAck = cmd.execute(ctx);
			
			if (cmAck instanceof ErrorAck ) {
				log.info("Effettuato il roolback applicativo del command idCommand=" + codCommand + "");
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

	public CommandAck executeCommand(BeanCommand bc, HashMap map, String processId)
			throws CommandException {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private void setMapToContext(HashMap map, Context ctx)
	{
		if (map != null && map.size() > 0)
		{
			Set keys = map.keySet();
			Iterator it = keys.iterator();
			while (it.hasNext())
			{
				Object key = (Object) it.next();
				ctx.put(key, map.get(key));
			}
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
					
					try	{
						conn.rollback();
						conn.close();
					} catch (SQLException e1) {
						log.error(e1);
					}
				}
			}
		}catch(Exception e)		{
			log.error(e);
		}
	}	
	
	
	
	@SuppressWarnings("unused")
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

}
