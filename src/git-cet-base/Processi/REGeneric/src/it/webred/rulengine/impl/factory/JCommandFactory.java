package it.webred.rulengine.impl.factory;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Properties;

import it.webred.rulengine.Command;
import it.webred.rulengine.db.model.RCommand;

import it.webred.rulengine.impl.BaseCommandFactory;

import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.Variable;


import org.apache.log4j.Logger;


/**
 * Classe factory per i soli comandi jelly
 * 
 * @author webred
 *
 */
public class JCommandFactory extends BaseCommandFactory {
	
	private static final Logger log = Logger.getLogger(JCommandFactory.class.getName());
	
	/*
	 * File di cfg base delle catene jelly: contiene
	 * informazioni generiche sulla catena jelly
	 */
	private Properties jchainbasecfg = null;
	
	
	public JCommandFactory() {
		super();
		
		try {
			jchainbasecfg = this.getConfigReference("/config/jchainbase.properties");
		}catch(Exception e){
			log.error("Eccezione inizializzazione cfg chainbase: "+e.getMessage(),e);
		}
	}

	

	public Command getCommand(Properties objectConfig, boolean evenSystemCommand) throws CommandFactoryException {
		
		Command cmd = null;
		
		//TODO: implementare quando e se servirà
		log.warn("Attenzione !! Implementare il metodo quando e se servirà");
		
		return cmd;
	}

	
	/**
	 * Il metodo restituisce un istanza del comando da eseguire.
	 * 
	 * Attenzione !!
	 * Il parametro evenSystemCommand servo solo per differenziare il tipo di comando jelly da 
	 * prendere in considerazione, se con parametri fissi o dinamici impostati via web dall'utente 
	 */
	public Command getCommand(String codCommand, boolean evenSystemCommand) throws CommandFactoryException {
		
		Command cmd = null;
		RCommand element = null;
		String jChainCmdClass = null;
		
		try {

			/*
			 * La gestione del recupero dei file di cfg della catena jelly è qui perché 
			 * tutti i metodi precedenti convergono in questo getCommand
			 */
			log.debug("Recupero e caricamento files di cfg della catena jelly in esame");
			String jchaincfg = "/config/CHAINS/"+codCommand.toLowerCase()+".properties";
			String jscript = "/config/CHAINS/"+codCommand.toLowerCase()+".xml";
			
			InputStream jxml = JCommandFactory.class.getResourceAsStream(jscript);
			
			if(evenSystemCommand) {  //true
				jChainCmdClass = this.jchainbasecfg.getProperty("rengine.jelly.generic.chain.class");
			}
			else {    //false
				jChainCmdClass = this.jchainbasecfg.getProperty("rengine.jelly.params.chain.class");
			}
			
			log.info("Classe comando catena jelly da caricare ["+jChainCmdClass+"]");
			
			//recupero bean command della catena jelly
			BeanCommand bc = new BeanCommand(cDao.getRCommandByCodCommand2(codCommand));
			
			Class cl = Class.forName(jChainCmdClass);
			Constructor cnst = cl.getConstructor(new Class[]
			                         { BeanCommand.class, 
					                   Properties.class, 
					                   InputStream.class });
			
			cmd = (Command) cnst.newInstance(
					new Object[] { bc,this.getConfigReference(jchaincfg),jxml });
			
			log.info("Impostazione riferimento DAO launcher");
			
		}catch(Exception e) {
			log.error("Errore in getCommand" + jChainCmdClass,e);
			throw new CommandFactoryException("Errore in getCommand" + jChainCmdClass, e);
		}
		
		return cmd;
	}
	
	
	@Override
	public Command getEventCommand(String codCommand, List<Variable> llvv)
			throws CommandFactoryException {
		
		Command cmd = null;
		RCommand element = null;
		String jChainCmdClass = null;
		
		try {
			/*
			 * La gestione del recupero dei file di cfg della catena jelly è qui perché 
			 * tutti i metodi precedenti convergono in questo getCommand
			 */
			log.debug("Recupero e caricamento files di cfg della catena jelly in esame");
			String jchaincfg = "/config/CHAINS/"+codCommand.toLowerCase()+".properties";
			String jscript = "/config/CHAINS/"+codCommand.toLowerCase()+".xml";
			
			InputStream jxml = JCommandFactory.class.getResourceAsStream(jscript);
			
			jChainCmdClass = this.jchainbasecfg.getProperty("rengine.jelly.event.chain.class");
			
			log.info("Classe comando catena jelly da caricare ["+jChainCmdClass+"]");
			
			//recupero bean command della catena jelly
			BeanCommand bc = new BeanCommand(cDao.getRCommandByCodCommand2(codCommand));
			
			Class cl = Class.forName(jChainCmdClass);
			Constructor cnst = cl.getConstructor(new Class[]
			                         { BeanCommand.class, 
					                   Properties.class, 
					                   InputStream.class,
					                   List.class});
			
			cmd = (Command) cnst.newInstance(
					new Object[] { bc,this.getConfigReference(jchaincfg),jxml, llvv });
			
			log.info("Impostazione riferimento DAO launcher");
			
		}catch(Exception e) {
			throw new CommandFactoryException("Errore recupero comando basato su evento" + e);
		}
		
		return cmd;
	}
	
	
	
	/**
	 * Recupero istanza file cfg generale della catena jelly
	 * 
	 * @return
	 */
	public Properties getJchainbasecfg() {
		return this.jchainbasecfg;
	}
	
	
	
	


	/**
	 * Recupero file cfg 
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private Properties getConfigReference(String path) throws Exception {
		try {
			InputStream is = BaseCommandFactory.class.getResourceAsStream(path);
			Properties p = new Properties();
			p.load(is);
			return p;
		} catch (Exception e ) {
			log.error("Problema reperimento configurazione", e);
			throw new CommandFactoryException("Problema reperimento configurazione:" + path, e);
		}
	}
	
}
