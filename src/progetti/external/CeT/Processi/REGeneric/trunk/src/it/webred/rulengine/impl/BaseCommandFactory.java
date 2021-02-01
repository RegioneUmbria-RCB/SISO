package it.webred.rulengine.impl;

import java.util.List;
import java.util.Properties;

import it.webred.rulengine.Command;

import it.webred.rulengine.db.dao.IRCommandAckDAO;
import it.webred.rulengine.db.dao.IRCommandDAO;
import it.webred.rulengine.db.dao.IRCommandLaunchDAO;
import it.webred.rulengine.db.dao.impl.RCommandAckDAOImpl;
import it.webred.rulengine.db.dao.impl.RCommandDAOImpl;
import it.webred.rulengine.db.dao.impl.RCommandLaunchDAOImpl;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.factory.CommandFactoryException;
import it.webred.rulengine.impl.factory.JCommandFactory;
import it.webred.rulengine.impl.factory.RCommandFactory;
import it.webred.rulengine.type.Variable;

import org.apache.log4j.Logger;


public abstract class BaseCommandFactory {
	
	private static final Logger log = Logger.getLogger(BaseCommandFactory.class.getName());
	
	
	protected IRCommandDAO cDao;
	protected IRCommandLaunchDAO rclDao;
	protected IRCommandAckDAO rckDao;
	
	/**/
	public BaseCommandFactory() {
		super();
		
		cDao = new RCommandDAOImpl();
		rclDao = new RCommandLaunchDAOImpl();
		rckDao = new RCommandAckDAOImpl();
	}
	/**/

	/**
	 * 
	 * @param type  J: catena jelly command
	 * 				R: catena jelly rule command
	 * @return
	 */
	public static BaseCommandFactory getFactory(String type) {
		
		BaseCommandFactory bcf = null;
		
		if(type != null) {
			if(type.equals("J")) {
				bcf = new JCommandFactory(); 
			}
			
			if(type.equals("R")) {
				bcf = new RCommandFactory();
			}
		}
		
		return bcf;
	}
	
	
	
	
	/*
	 *  M E T O D I   A S T R A T T I 
	 */
	
	public abstract Command getCommand(String codCommand , boolean evenSystemCommand) throws CommandFactoryException;
	
	/**
	 * Al metodo viene passato in input il riferimento al proprio file di configurazione
	 * sia che si tratti di una catena jelly che di una regola di catena jelly
	 * 
	 * @param evenSystemCommand
	 * @param objectConfig
	 * @return
	 * @throws CommandException
	 */
	public abstract Command getCommand(Properties objectConfig, boolean evenSystemCommand) throws CommandFactoryException;
	
	
	/**
	 * Metodo esclusivo per il recupero di un comando da lanciare tramite evento
	 * @param codCommand
	 * @param llvv
	 * @return
	 * @throws CommandException
	 */
	public abstract Command getEventCommand(String codCommand, List<Variable> llvv) throws CommandFactoryException;
	
		
}
