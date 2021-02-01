package it.webred.rulengine.chains.bridge;


import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.brick.bean.SubscriberNotFoundAck;
import it.webred.rulengine.brick.bean.WarningAck;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.exceptions.ImportFilesException;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.ContextException;

import it.webred.rulengine.impl.BaseCommandFactory;
import it.webred.rulengine.impl.ContextBase;
import it.webred.rulengine.impl.factory.RCommandFactory;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.Variable;

import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.impl.TypeFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.log4j.Logger;


/**
 * Classe ponte tra contesto RE e contesto jelly:
 * si occupa di gestire l'esecuzione di un singolo comando o regola
 * compreso nella catena dello script jelly.
 * 
 * Viene definito all'interno dello script jelly 
 * per la configurazione del singolo comando
 * 
 * @author webred
 *
 */
public class JellyCommandBridge extends BaseCommandBridge {

	private static final Logger log = Logger.getLogger(JellyCommandBridge.class.getName());
	
	private String codiceComando;
	private String connessione;
	
	private LinkedList<Object> paramsIn = new LinkedList<Object>();
	
	//oggetto di ritorno del comando
	private HashMap<String, Object> risultati = new HashMap<String, Object>();
	
	private Context ctx;
	private CommandAck retAck = null;
	

	@SuppressWarnings("unchecked")
	public void setParametro(Object o) {
		
		try {
			
			//scorro la mappa statica globale alla ricerca dei parametri complessi provenienti da jelly
			for(int i=1; i<=_globalBridgeParams.size();i++) {
				Object obj = _globalBridgeParams.get(BaseCommandBridge.BCBPAR+i);
				
				//aggiungo al contesto senza specificare il type
				if(obj != null)
					ctx.addDeclarativeType(obj.toString(), new Variable(obj.toString(),"",obj));
			}
			
			//ripulisco la mappa globale
			_globalBridgeParams.clear();
			
			if(o != null) {
				//log.debug("Object o: "+o.toString());
				ComplexParam param = (ComplexParam) TypeFactory.getType(o,"it.webred.rulengine.type.ComplexParam",ctx.getDeclarativeType());
				
				LinkedHashMap<String , ComplexParamP> params = param.getParams();
				Iterator<Entry<String, ComplexParamP>> entryIter = params.entrySet().iterator(); 
				
				while(entryIter.hasNext()) {
					Map.Entry<String, ComplexParamP> entry = (Map.Entry<String, ComplexParamP>) entryIter.next();
					ComplexParamP p = entry.getValue();
					paramsIn.add(p.getValore());
				}
			}
		}catch (Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
		}
	}
	
	
	
	
	public void setContext(Context ctx) {
		this.ctx = ctx;
	}
	
	
	/**
	 * Il metodo viene chiamato dal comando jelly per riprendere il contesto aggiornato
	 * dopo l'esecuzione della regola
	 * 
	 * @return
	 */
	public Context getCtx() {
		return this.ctx;
	}


	public void run() throws Exception {
		CommandAck esito = null;
		
		try {	
			RCommandFactory rf = (RCommandFactory) BaseCommandFactory.getFactory("R");
			
			log.debug("Recupero path file cfg della regola corrente ["+this.codiceComando+"]");
			String rulecfgpath = "/config/RULES/"+this.codiceComando.toLowerCase()+".properties";
			log.debug("Path recuperato ["+rulecfgpath+"]");
			
			Command cmd = rf.getCommand(rf.getConfigReference(rulecfgpath), true);

			try {
				log.debug("Acquisizione paramentri di input da file di cfg ");
				Properties p = cmd.getRuleConfig();
				Integer nofparamsin = Integer.parseInt(p.getProperty("rengine.rule.number.params.in"));
				
				//contesto relativo all'ambito della singola regola da eseguire
				Context ctx1 = new ContextBase();
				
				try {
					log.debug("Copia degli attributi di contesto");
					ctx1.copiaAttributi(ctx);
					
				}catch (ContextException e) {
					log.error("Errori opiando attributi dl contesto");
					ctx1 = null;
				}
				
				int i=0;
				for(int j=1; j<=nofparamsin; j++) {
					try 
					{
						if (i < paramsIn.size()) {
							ctx1.put(p.getProperty("rengine.rule.param.in."+j+".descr"), paramsIn.get(i));
						}
						
						i++;
					} catch (Exception e) {
						throw new CommandException("Possibile errore nel passaggio del parametro " + i+1);
					}
				}
				
				log.debug("Inserimento del riferimento connessione nel contesto");
				ctx1.put("connessione",this.connessione+"_"+ctx1.getBelfiore());
				
				log.debug("Run command");
				esito = cmd.run(ctx1);
				
				log.debug("Parametri di uscita dal comando per registrazione");
				Integer nofparamsout = Integer.parseInt(p.getProperty("rengine.rule.number.params.out"));
				for(int k=1; k<=nofparamsout; k++) {
					
					Object obj = ctx1.get(p.getProperty("rengine.rule.param.out."+k+".descr"));
					
					//inserimento degli eventuali par di out nella Map 
					risultati.put(p.getProperty("rengine.rule.param.out."+k+".descr"),obj);
					
					//inserimento anche nella map statica globale
					_globalBridgeParams.put(BaseCommandBridge.BCBPAR+k, obj);
				}
				
				//log.debug("Copia degli attributi di contesto");
				this.ctx.copiaAttributi(ctx1);
				
				
				//controllo ack di ritorno dalla regola
				
				
				if(esito instanceof ApplicationAck) {
					log.info(this.codiceComando + "- Regola della catena eseguita con esito positivo");
				}
				else if(esito instanceof RejectAck) {
					log.warn(this.codiceComando + "- Esecuzione comando rigettata: " +esito.getMessage());
				}
				else if(esito instanceof WarningAck) {
					log.warn(this.codiceComando + "-  Regola della catena eseguita con segnalazioni: "+esito.getMessage());
				}
				else if(esito instanceof NotFoundAck) {
					log.warn(this.codiceComando + "- Regola della catena interrotta con segnalazioni: "+esito.getMessage());
					//throw new CommandException("Esito esecuzione regola con segnalazioni: "+esito.getMessage());
				}
				else if(esito instanceof SubscriberNotFoundAck) {
					log.warn(this.codiceComando + "- Regola della catena interrotta con segnalazioni: "+esito.getMessage());
					throw new CommandException("Esito esecuzione regola con segnalazioni: "+esito.getMessage());
				}
				else if(esito instanceof ErrorAck)  {  
					log.warn(this.codiceComando + "- Regola della catena in errore: "+esito.getMessage());
					
					// se e' un errorack ma relativo alla importazione dei file txt allora si procede al lancio dell'eccezione, altrimenti
					// si rimanda indietro come eccezione gestita e non bloccante (CommandException)
					if (((ErrorAck)esito).getException() instanceof ImportFilesException) 
						throw (ImportFilesException)((ErrorAck)esito).getException();	
					else
						throw new CommandException("Esito esecuzione regola negativo: "+esito.getMessage());
					
				}
				
			}catch(CommandException ce){
				log.error("Eccezione gestita esecuzione di un Comando", ce);
				esito = new ErrorAck(ce.getMessage());
				throw ce;
			}catch (Exception e) {
				log.error("Eccezione imprevista esecuzione di un Comando", e);
				esito = new ErrorAck(e.getMessage());
				throw e;
			} finally {
				setRetAck(esito);
			}
			
		/*
		 * Attenzione !! Qualunque ezccezione lanciata da questa classe viene catturata dalla classe
		 * JellyScriptCommand come JellyException
		 */
		}catch(CommandException ce) { 
			throw new CommandException(ce.getMessage());
		}catch(Exception e) {
			log.error("Eccezione runtime: "+e.getMessage(),e);
			throw new Exception(e.getMessage());
		}
	}
	
	
	
	public HashMap getRisultati()
	{
		return risultati;
	}

	public CommandAck getRetAck() {
		return retAck;
	}


	public void setRetAck(CommandAck retAck) {
		if (this.retAck==null) {
			this.retAck = retAck;	
		}	
	}


	public String getConnessione() {
		return connessione;
	}


	public void setConnessione(String connessione) {
		this.connessione = connessione;
	}


	public String getCodiceComando() {
		return codiceComando;
	}


	public void setCodiceComando(String codiceComando) {
		this.codiceComando = codiceComando;
	}


	
}
