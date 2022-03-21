package it.webred.rulengine.chains;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.XMLOutput;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.brick.bean.WarningAck;
import it.webred.rulengine.chains.bridge.JellyCommandBridge;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.BaseCommandFactory;
import it.webred.rulengine.impl.ContextBase;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.impl.factory.JCommandFactory;
import it.webred.rulengine.type.Variable;


/**
 * Comando jelly per la gestione esclusiva dei comandi lanciati da un evento configurato
 * 
 * @author webred
 *
 */
public class JellyScriptEventCommand extends Command implements Rule {
	private static final Logger log = Logger.getLogger(JellyScriptEventCommand.class.getName());
	
	private JCommandFactory jcfactory = null;
	
	//lista di variabili con info su comando di provenienza che ha scatenato l'evento in esecuzione
	private List<Variable> llvv;
	
	public JellyScriptEventCommand(BeanCommand bc) {
		super(bc);
	}
	
	public JellyScriptEventCommand(BeanCommand bc, Properties jchaincfg, InputStream jscript, List<Variable> llvv) {
		super(bc, jchaincfg, jscript);
		
		this.llvv = llvv;
		jcfactory = (JCommandFactory) BaseCommandFactory.getFactory("J");
	}



	@SuppressWarnings("unchecked")
	@Override
	public CommandAck run(Context ctxRE) throws CommandException {
		
		CommandAck cmdAck = null;
		String script = null;
		Context ctxRE1 = null;
		
		
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			
			InputSource isource = new InputSource(_jscript);
			
			OutputStream output = new ByteArrayOutputStream();
		    JellyContext jcontext = new JellyContext();
		    
		    XMLOutput xmlOutput = XMLOutput.createXMLOutput(output);
		    ctxRE1 = new ContextBase();
			ctxRE1.copiaAttributi(ctxRE);
					
			//nel ctxRE è possibile mettere param da passare al contesto jelly
			log.debug("Recupero parametri input della catena jelly");
			Integer nofparam = Integer.parseInt(_jchaincfg.getProperty("rengine.jchain.in.nof"));
			
			for(int i=1; i<=nofparam; i++) {
				String nome = _jchaincfg.getProperty("rengine.jchain.in."+i+".key");
				String valore = _jchaincfg.getProperty("rengine.jchain.in."+i+".value");	
				
				ctxRE1.addDeclarativeType(nome, new Variable(nome,"java.lang.String",valore));
			}
			
			log.info("Salvataggio variabili evento su contesto");
			for(Variable v: this.llvv) {
				ctxRE1.addDeclarativeType(v.getName(), v);
				log.debug("#### Variabile evento inserita nel contesto ["+v.getName()+"]");
			}
			
			jcontext.setVariable("ctx", ctxRE1);
			jcontext.runScript(isource, xmlOutput);			
			xmlOutput.flush();
			
			log.debug("Impostazioni eventuali segnalazioni di esecuzione comando");
			java.util.Map v = jcontext.getVariables();
			java.util.Iterator<Map.Entry> entryIter = v.entrySet().iterator(); 
			
			while(entryIter.hasNext()) {
			
				Map.Entry entry = (Map.Entry) entryIter.next();
				
				if (entry.getValue() instanceof JellyCommandBridge) {
					
					ctxRE.copiaAttributi(((JellyCommandBridge)entry.getValue()).getCtx());
					cmdAck = ((JellyCommandBridge)entry.getValue()).getRetAck();
					
					if (cmdAck instanceof ErrorAck || cmdAck instanceof RejectAck) {
						String currMessage = cmdAck.getMessage();
						cmdAck.setMessage("Elaborazione comando jelly in errore ["+currMessage+"]");
					}
					else if(cmdAck instanceof NotFoundAck) {
						String currMessage = cmdAck.getMessage();
						cmdAck.setMessage("Elaborazione comando terminata con segnalazioni di errore ["+currMessage+"]");
					}
					else if(cmdAck instanceof WarningAck) {
						String currMessage = cmdAck.getMessage();
						cmdAck.setMessage("Elaborazione comando jelly eseguita con segnalazioni ["+currMessage+"]");
					}
					else if(cmdAck instanceof ApplicationAck) {
						String currMessage = cmdAck.getMessage();
						cmdAck.setMessage("Elaborazione comando jelly eseguita con successo ["+currMessage+"]");
					}
				}
			}
		}catch (JellyException e) {
			if(e.getCause() instanceof CommandException) {
				log.error("############# Attenzione!! "+e.getCause().getMessage());
				cmdAck = new NotFoundAck(e.getCause().getMessage());
			}
			else {
				log.error("Script XML con problemi",e);
				cmdAck = new ErrorAck(e);
			}
		}catch (ParserConfigurationException e1) {
			log.error("Errore Xml Builder"+ script,e1);
			cmdAck = new ErrorAck(e1);
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
			cmdAck = new ErrorAck(e);
		}finally {
			ctxRE1 = null;
		}
		
		return cmdAck;
	}
	
}
