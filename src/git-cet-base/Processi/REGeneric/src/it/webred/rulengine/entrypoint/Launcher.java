package it.webred.rulengine.entrypoint;

import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.Variable;

import java.util.HashMap;
import java.util.List;


/**
 
 * 
 * 
 * @author marcoric
 */
/**
 * Interfaccia di un launcher, componenti dedicati al lancio di un comamnd
 * 
 * @author sisani
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:25 $
 */
public interface Launcher
{
	/**
	 * Metodo per l'esecuzione di un Command in base al suo id.
	 * 
	 * @param idCommand - da lanciare
	 * @param processId 
	 * @throws CommandException
	 */
	public CommandAck executeCommand(String codCommand, String processId)
		throws CommandException;

	/**
	 * Metodo per l'esecuzione di un Command in base al suo id.
	 * 
	 * @param idCommand - da lanciare
	 * @param processId 
	 * @param map - elenco parametri per il command
	 * @throws CommandException
	 */
	public CommandAck executeCommand(String codCommand, HashMap map, String processId)
		throws CommandException;
	

	/**
	 * @param idCommand
	 * @param map - elenco parametri per il command
	 * @param processId
	 * @param variabili - elenco variabili da passare al command
	 * @param con - eventuale connessione , viene presa se il command è di tipo DbCommand
	 * @throws CommandException
	 */
	public CommandAck executeCommand(String codCommand, HashMap map, String processId, List<Variable> variabili, String con)
		throws CommandException;

	/**
	 * Metodo per l'esecuzione di un BeanCommand.
	 * 
	 * @param BeanCommand - da lanciare
	 * @param processId 
	 * @param map - elenco parametri per il command
	 * @throws CommandException
	 */
	public CommandAck executeCommand(BeanCommand bc, HashMap map, String processId)
		throws CommandException;

}