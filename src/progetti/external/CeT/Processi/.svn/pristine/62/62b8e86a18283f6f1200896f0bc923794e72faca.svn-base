package it.webred.rulengine;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;

import it.webred.rulengine.db.RulesConnection;


import it.webred.rulengine.db.dao.IRCommandLaunchDAO;

import it.webred.rulengine.db.dao.impl.RCommandLaunchDAOImpl;
import it.webred.rulengine.db.model.RCommandLaunch;
import it.webred.rulengine.db.model.RRuleParamOut;

import it.webred.rulengine.db.model.RRuleParamIn;

import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.ContextException;
import it.webred.rulengine.impl.CommandUtil;
import it.webred.rulengine.impl.ContextBase;

import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;
import it.webred.rulengine.type.Variable;
import it.webred.rulengine.type.def.DeclarativeType;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * Il command è una classe astratta, che viene estesa da tutti i brick. La
 * classe fornisce i metodi di base di un command, un oggetto di tipo
 * BeanCommand, una lista di tipo RRuleParamIn, che sono gli eventuali parametri
 * in ingresso del command e una lista di tipo RRuleParamOut, che sono gli
 * eventuali parametri in uscita di un command
 * 
 * @author sisani
 * @version $Revision: 1.14 $ $Date: 2010/01/08 13:54:43 $
 */
public abstract class Command
{
	private BeanCommand beanCommand;

	private static final Logger log = Logger.getLogger(Command.class.getName());
		
	private IRCommandLaunchDAO rclDao;

	
	/*
	 * File di configurazione jelly per recupero 
	 * impstazioni regole e parametri dello script
	 */
	protected Properties _jchaincfg = null;
	protected Properties _jrulecfg = null;
	protected InputStream _jscript = null;
	
	/**
	 * Costruttore che crea un Command prendendo come parametro di ingresso un
	 * BeanCommand, controllando che se si tratta di un BeanCommand che è una
	 * istanza di BeanChainCommand e il command è un'istanza di DbRule, setta la
	 * relativa connessione
	 * 
	 * @param bc
	 * 
	 */
	public Command(BeanCommand bc)
	{
		this.beanCommand = bc;
		
		//inizializzazione componente DAO
		this.rclDao = new RCommandLaunchDAOImpl();
	}
	
	/**
	 * Versione del costruttore a cui vengono passati i fle di cfg del comando jelly
	 * e non della regola
	 * 
	 */
	public Command(BeanCommand bc, Properties jchaincfg, InputStream jscript)
	{
		this.beanCommand = bc;
		
		/*
		 * Configurazioni chain jelly
		 */
		_jchaincfg = new Properties(jchaincfg);
		_jscript = jscript;
		
		//inizializzazione componente DAO
		this.rclDao = new RCommandLaunchDAOImpl();
	}
	
	/**
	 * Versione del costruttore a cui viene passato il fle di cfg della regola
	 * 
	 */
	public Command(BeanCommand bc, Properties jrulecfg)
	{
		this.beanCommand = bc;
		
		/*
		 * Configurazioni regola
		 */
		_jrulecfg = new Properties(jrulecfg);
		
		//inizializzazione componente DAO
		this.rclDao = new RCommandLaunchDAOImpl();
	}
	

	

	public Properties getRuleConfig() {
		return _jrulecfg;
	}

	public CommandAck execute(Context ctx) throws CommandException
	{
		return execute(ctx, true);
	}
	
	
	/**
	 * Metodo che viene richiamato per l'esecuzione di un command. Al suo
	 * interno vengono eseguiti i seguenti passi: - controllo della presenza di
	 * un CommandLaunch, e eventuale salvataggio, - preparazione del contesto,
	 * con i parametri in ingresso relativi al Command, - esecuzione dello
	 * stesso, - controllo presenza anomalie, - eventuale salvataggio nel
	 * contesto di parametr in uscita. Il metodo viene completamente ridefinito
	 * nelle Chain.
	 * 
	 * @param ctx -
	 *            Contesto in cui sono memeorizzati tutti i parametri che
	 *            servono ai command.
	 * @param saveAck - indica se salvare gli ack applicativi oppure no, ignorarli e lasciare al chiamante dire che va tutto ok.
	 * @return CommandAck
	 */
	@SuppressWarnings("unchecked")
	public CommandAck execute(Context ctx, boolean saveAck) throws CommandException
	{
		log.debug("execute Command");
		
		RCommandLaunch rcl = null;
		CommandAck bi = null;
		boolean controlloUpdateLaunch = false;
		String processId = "";
		
		try {
			processId = ctx.getProcessID();
			rcl = getRCommandLaunch(processId);
			
			if (rcl == null)
			{
				log.info("Salvataggio riferimento RCommandLaunch");
				rcl = saveRCommandLaunch(processId,ctx.getBelfiore(),ctx.getIdSched());
				
				ctx.addDeclarativeType("RULENGINE.COD_COMMAND", new Variable("RULENGINE.COD_COMMAND","java.lang.String",rcl.getRCommand()==null?null:rcl.getRCommand().getCodCommand()));
				ctx.addDeclarativeType("RULENGINE.ID_COMMAND_LAUNCH", new Variable("RULENGINE.ID_COMMAND_LAUNCH","java.lang.Integer",rcl.getId()));
				ctx.addDeclarativeType("RULENGINE.PROCESSID", new Variable("RULENGINE.PROCESSID","java.lang.String",processId));
				controlloUpdateLaunch = true;
					
				// leggo tutte le fonti dati censite nel DWH (se presente un DWH)
				// VISTO CHE POTREBBERO SERVIRMI SE IL COMMAND e' PARTE DI UN PROCESSO DI CARICAMENTO
				this.leggiFontiDati(ctx);
				
				if(saveAck) {
					if (rcl != null) {
						CommandUtil.saveRCommandAck(rcl, new ApplicationAck("Inizio esecuzione"),this.beanCommand);
					}
				}
			}
			
			//nel caso di schedulazione con evento successivo la parte
			//if (rcl == null) viene saltata quindi ricarico la lista
			if(ctx.getListaEnteSrgente().isEmpty())
				this.leggiFontiDati(ctx);
				

			Context ctx1 = new ContextBase();

			ctx1.copiaAttributi(ctx);

			log.info("Lancio del metodo run()");
			bi = this.run(ctx1);
				
			
			if (this instanceof Rule )
			{
				if (bi instanceof ApplicationAck)
				{					
					log.info("Copia parametri DeclarativeType da contesto di ritorno");
					for (Map.Entry<String, DeclarativeType> item : ctx1.getDeclarativeType().entrySet())
					{
						String key = item.getKey();
						DeclarativeType dt = item.getValue();
						ctx.addDeclarativeType(key, dt);
					}
				}
			}
						
			log.info("Copia elenco connessioni utilizzate da contesto di ritorno");
			for (Map.Entry<String, Connection> itemConnection : ctx1.getConnections().entrySet())
			{
				String key = itemConnection.getKey();
				Connection conn = itemConnection.getValue();
				ctx.addConnectionNoTransactionIsolation(key, conn);
			}						

			return bi;

		}catch (ContextException e)		{
			bi = new ErrorAck(e);
			return bi;
		}
		catch (CommandException e)
		{
			bi = new ErrorAck(e);
			return bi;
		}		
	
		catch (Exception e)
		{
			bi = new ErrorAck(e);
			return bi;
		}
		finally
		{
			try
			{
				if(saveAck)
					if (rcl != null)
						CommandUtil.saveRCommandAck(rcl, bi,this.beanCommand);
					else
					{
						String msg = "Non possibile reperire un identificativo per il lancio del comando";
						log.error(msg);
						bi = new ErrorAck("Non possibile reperire un identificativo per il lancio del comando");
						return bi;
					}
			}
			catch (Exception e)
			{
				log.error("Errore salvatagglio ACK", e);
			}

			if(saveAck)
			if (controlloUpdateLaunch)
			{
				try
				{
					CommandUtil.updateRCommandLaunch(processId);
				}
				catch (Exception e)
				{
					log.error("Problemi nella valorizzazione della DATE_END di R_COMAND_LAUNCH", e);
					bi = new ErrorAck("Non possibile reperire un identificativo per il lancio del comando");
					return bi;
				}
			}
		}
	}

	
	/**
	 * Legge nel db dwh la tabella di SIT_ENTE_SORGENTE
	 * @param ctx
	 * @throws CommandException 
	 */
	private void leggiFontiDati(Context ctx) throws CommandException {
		Connection conn = null;
		try {
			log.info("Recupero manuale riferimento RuleConnection DAO");
			conn = RulesConnection.getConnection("DWH_"+ctx.getBelfiore());
		} catch (Exception e) {
			log.warn("NON PRESENTE LA CONNESSIONE DWH IN RulEngine");
			return;
		}
		if (conn==null) {
			log.warn("NON PRESENTE LA CONNESSIONE DWH IN RulEngine");
			return;
		}
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select s.*, e.codent from SIT_ENTE_SORGENTE s, SIT_ENTE e where s.fk_ente = e.id ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				BeanEnteSorgente bes = new BeanEnteSorgente();
				int disabilitaStorico = rs.getInt("DISABILITA_STORICO");
				int inReplace = rs.getInt("FORNITURA_IN_REPLACE");
				
				//chiamata ai parametri amprofiler
				ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
				AmKeyValueExt paramStorico = cdm.getAmKeyValueExtByKeyFonteComune("disabilita.storico", ctx.getBelfiore(), String.valueOf(rs.getInt("id")));
				AmKeyValueExt paramReplace = cdm.getAmKeyValueExtByKeyFonteComune("fornitura.in.replace", ctx.getBelfiore(), String.valueOf(rs.getInt("id")));
				if(paramStorico != null)
					disabilitaStorico = Integer.parseInt(paramStorico.getValueConf());
				if(paramReplace != null)
					inReplace = Integer.parseInt(paramReplace.getValueConf());
				
				if (disabilitaStorico == 1)
					bes.setDisabilitaStorico(true);
				else
					bes.setDisabilitaStorico(false);
				if (inReplace == 1 || inReplace == 2)
					bes.setInReplace(true);
				else
					bes.setInReplace(false);
				
				bes.setInReplaceValue(inReplace);
				bes.setCodSorgente(rs.getString("COD_SORGENTE"));
				bes.setDescrizione(rs.getString("descrizione"));
				bes.setId(rs.getInt("id"));
				ctx.addEnteSorgente(bes);
				
				//come is omari scrivo sempre
				ctx.addDeclarativeType("RULENGINE.CODENTE", new Variable("RULENGINE.CODENTE","java.lang.String",rs.getString("codent")));
				ctx.getDeclarativeType("RULENGINE.CODENTE");

			}
		} catch (Exception e) {
			log.error("Problemi recupero dati sulla tabella SIT_ENTE_SORGENTE",e);
			throw new CommandException("Problemi recupero dati sulla tabella SIT_ENTE_SORGENTE");
		} finally {
				try {
					if (rs!=null)
						rs.close();
					if (stmt!=null)
						stmt.close();				
					if (conn!=null)
						conn.close();				
				} catch (SQLException e) {
					log.error("Problemi sql su chiousura query",e);
					throw new CommandException("Problemi sql su chiousura query");
				}

		}
		
		
		
	}
	
	/**
	 * Metodo da implementare in ogni command, implementa il comportamento
	 * specifico di ogni command.
	 * 
	 * @param ctx1 -
	 *            Context locale che contiene e restituisce i parametri
	 *            specifici di una rule.
	 */
	public abstract CommandAck run(Context ctx1)
		throws CommandException;

	public BeanCommand getBeanCommand()
	{
		return beanCommand;
	}



	/**
	 * Il metodo prende la lista dei parametri dal filke di configurazione della regola
	 * e nn più da tabella
	 * 
	 * @return List - Lista di RRuleParamIn del Command
	 */
	public List<RRuleParamIn> getParametersIn(Properties rconfig)
	{
		List<RRuleParamIn> params = new ArrayList<RRuleParamIn>();
		
		try {	
			
			int paramCount = Integer.parseInt(rconfig.getProperty("rengine.rule.number.params.in"));
			
			//i param sono inseriti secondo l'ordine impostato nel file di cfg
			for(int i=1; i<=paramCount; i++) {
				RRuleParamIn p = new RRuleParamIn();
				p.setType(rconfig.getProperty("rengine.rule.param.in."+i+".type"));
				p.setDescr(rconfig.getProperty("rengine.rule.param.in."+i+".descr"));
				
				params.add(p);
			}
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
			params = null;
		}
		
		return params;
	}
	
	public List<RRuleParamOut> getParametersOut(Properties rconfig)
	{
		List<RRuleParamOut> params = new ArrayList<RRuleParamOut>();
		
		try {	
			
			int paramCount = Integer.parseInt(rconfig.getProperty("rengine.rule.number.params.out"));
			
			//i param sono inseriti secondo l'ordine impostato nel file di cfg
			for(int i=1; i<=paramCount; i++) {
				RRuleParamOut p = new RRuleParamOut();
				p.setType(rconfig.getProperty("rengine.rule.param.out."+i+".type"));
				p.setDescr(rconfig.getProperty("rengine.rule.param.out."+i+".descr"));
				
				params.add(p);
			}
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
			params = null;
		}
		
		return params;
	}


	/**
	 * Metodo per il salvataggio di un Command Launch in base al ProcessId.
	 * 
	 * @param processId -
	 *            codice univoco del processo da salvare
	 * @return RCommandLaunch - Ritorna RCommandLaunch salvato con l'id
	 *         assegnato
	 */
	private RCommandLaunch saveRCommandLaunch(String processId,String belfiore,Long idSched)
	{
		RCommandLaunch rcl = new RCommandLaunch();
		
		try	{
			rcl.setProcessid(processId);
			rcl.setBelfiore(belfiore);
			rcl.setDateStart(Calendar.getInstance().getTime());
			rcl.setIdSched(idSched);
			
			if (this.beanCommand.getRCommand().getId() != null) {
				rcl.setRCommand(this.beanCommand.getRCommand());
			} 
			else {
				rcl.setRCommand(null);
			}
			
			//recupero connessione al db RE dell'ente
			Connection reconn = RulesConnection.getConnection("DEFAULT");
			rcl.setId(rclDao.getNextID(reconn).intValue());
			this.rclDao.saveRCommandLaunch(reconn,rcl);
			
		}catch (Exception e)	{
			log.error("Eccezione: "+e.getMessage(),e);
		}

		return (rcl);
	}


	public RCommandLaunch getRCommandLaunch(String processId) throws Exception
	{
		return this.rclDao.getRCommandLaunchByProcessID(processId);
	}

	
}
