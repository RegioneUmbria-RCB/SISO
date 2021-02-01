package it.webred.rulengine.brick.core;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;

import it.webred.rulengine.db.model.RAbNormal;

import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.ContextBase;
import it.webred.rulengine.impl.bean.BeanCommand;

import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.RsIteratorConfig;
import it.webred.rulengine.type.Variable;

import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.bean.RsIteratorConfigCmds;
import it.webred.rulengine.type.bean.RsIteratorConfigP;
import it.webred.rulengine.type.def.DeclarativeType;
import it.webred.rulengine.type.impl.TypeFactory;
import it.webred.utils.DateFormat;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.RowSetMetaData;
import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;

/**
 * Regola che in base a due parametri, che sono un stringa in un determinato
 * formato e un java.sql.ResultSet, esegue per i record del ResultSet le regole
 * descritte nel file xml. Le anomalie vengono scritte tutte insieme, e non
 * viene farmata la catena se avvengono degli errori all'interno del ciclo sul
 * ResultSet
 *
 * La cfg della regola è su file properties
 *
 * @author sisani
 * @version $Revision: 1.4 $ $Date: 2007/09/26 12:41:14 $
 */
public class RsIteratorAvanzato extends Command implements Rule
{
	private static final Logger log = Logger.getLogger(RsIteratorAvanzato.class.getName());

	

	public RsIteratorAvanzato(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}



	@SuppressWarnings("unchecked")
	@Override
	public CommandAck run(Context ctx)throws CommandException
	{
		ResultSet rs = null;
		
		try {
			log.debug("Recupero Resulset da parametro di contesto");
			rs = (ResultSet)ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));

			ComplexParam paramXML = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			HashMap<String,ComplexParamP> pars2 = paramXML.getParams();
			Set set2 = pars2.entrySet();
			Iterator it2 = set2.iterator();
			int i2 =1;
			String xmlitr = null;
			while (it2.hasNext()) {
				Entry entry = (Entry)it2.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				xmlitr = o.toString();
			}
			
			//rsiterator script xml preso da file di cfg
			RsIteratorConfig rsic = 
				(RsIteratorConfig)TypeFactory.getType(
						xmlitr,_jrulecfg.getProperty("rengine.rule.param.in.2.type"),
						ctx.getDeclarativeType());

			log.debug("INIZIARE RS ITERATOR");
			
			
			CommandAck ok = null;
			int giro = 0;
			int conteggio = 0;
			List<RAbNormal> anomalie = new ArrayList<RAbNormal>();
			
			ResultSetMetaData rsMeta = rs.getMetaData();
			//RowSetMetaData rsMeta = (RowSetMetaData)rs.getMetaData();

			int numeroColonne = rsMeta.getColumnCount();
			long t1 = System.currentTimeMillis();
			
			while (rs.next())
			{
				// riempo un hashmap di variabili con il valore dei campi in select del corrente record
				HashMap<String,DeclarativeType> variabiliCampi = (HashMap<String,DeclarativeType>)(ctx.getDeclarativeType()).clone();
				for (int i=1;i<=numeroColonne;i++) {

					if (rs.getObject(i)!=null) {
						//log.debug("Nome colonna "+i+": "+rsMeta.getColumnName(i));
						DeclarativeType dt = new Variable(rsMeta.getColumnName(i),rs.getObject(i).getClass().getName(), rs.getObject(i));
						//log.debug("Valore : "+rs.getObject(i));
						variabiliCampi.put(rsMeta.getColumnName(i).toUpperCase(),dt);
						//log.debug("[COLONNA] "+rsMeta.getColumnName(i));
					}
				}
				
				if (giro == 1000) {
					log.debug("TEMPO PARZIALE " + conteggio + " Record:" + (System.currentTimeMillis() - t1)  );
					t1 = System.currentTimeMillis();
					giro = 0;
				}
				
				giro++;
				conteggio++;
				
				try {
					Iterator itL = rsic.getCommands().iterator();

					while (itL.hasNext())
					{
						ContextBase ctx1 = null;
						
						try {
							RsIteratorConfigCmds bcmdI = (RsIteratorConfigCmds) itL.next();
							Command cmd = bcmdI.getCmd();
							// troppo log quando scorre
							//log.debug("Comando: "+cmd.getBeanCommand().getRCommand().getDescr());
							//log.debug(cmd.getBeanCommand().getRCommand().getCodCommand());
							//log.debug(cmd.getClass().getName());
							
							ctx1 = new ContextBase();
							ctx1.copiaAttributi(ctx);
							
							// Nel caso fosse un DbCommand questo mi torna senza la connection settata.
							// perché non lo sto istanziando da una catena.
							// LA FORZO IO
							if (cmd instanceof DbCommand) {
								((DbCommand)cmd).setConnectionName((String)ctx.get("connessione"));
							}
							else {
								ctx1.put("connessione", (String)ctx.get("connessione"));
							}
							

							//scorrimento parametri input da passare alla regola
							Iterator itParam =  bcmdI.getParam().iterator();
							while (itParam.hasNext())	{
								
								RsIteratorConfigP bp = (RsIteratorConfigP) itParam.next();
								
								//dest equivale al nome del parm in input della regola che verrà chiamata
								ctx1.put(bp.getDest(), null);
								
								if (bp.getId() != null )	{
									
									if (!bp.getId().equals("field") && !bp.getId().equals("const"))
										throw new CommandException("Si accetta solo field o const nella definizione del tipo di parametro");
									
									if (bp.getId().equals("const") || bp.getId().equals("field"))
									{
										Object o = null;
										try {
											
											if (bp.getId().equals("field")) {
												 o = rs.getObject(bp.getValue());
											}
												
											if (bp.getId().equals("field") && o!=null) {
												ctx1.put(bp.getDest(), TypeFactory.getType(o, o.getClass().getName()));
											}
											else if (bp.getId().equals("const") && bp.getValue() != null && !bp.getValue().equals("")) {
												ctx1.put(bp.getDest(), TypeFactory.getType(bp.getValue(), bp.getType(),variabiliCampi));
											}
											else if (bp.getValue() == null || bp.getValue().equals("")) {
												ctx1.put(bp.getDest(), null);
											}
										} catch (Exception e) {
											log.error("ATTENZIONE! " + bp.getId() + ": " + bp.getValue());
											log.error("ATTENZIONE! Iterazione con configurazione errata",e);
											throw new CommandException(e);	
										}
									} 
								}
							}
							
							ok = cmd.run(ctx1);
							
							boolean anomalievf = false;
							if (ok instanceof ApplicationAck)
							{
								if (((ApplicationAck) ok).getAbn().size() > 0)
									anomalievf = true;
							}
							if (ok instanceof RejectAck || ok instanceof ErrorAck)
								anomalievf = true;

							if (anomalievf)
							{
								if (ok instanceof ApplicationAck)
									anomalie.addAll(((ApplicationAck) ok).getAbn());
								else
								{
									RAbNormal rabn = new RAbNormal();
									rabn.setLivelloAnomalia(bcmdI.getLivelloAnomalie());
									Iterator keys = rsic.getKeyColumns().iterator();
									String id_ab_normal = "";
									while (keys.hasNext())
									{
										String chiave = (String) keys.next();
										log.debug("Campo chiave individuato: " + chiave);
										Object dato =null;
										if (rs.getObject(chiave) instanceof Timestamp || rs.getObject(chiave) instanceof Date)
											dato = DateFormat.dateToString((java.util.Date)rs.getObject(chiave),"yyyyMMdd HHmmss");
										else
											dato = rs.getObject(chiave);
										id_ab_normal = id_ab_normal + "#" + rs.getObject(chiave);
									}
									if (id_ab_normal != null && !id_ab_normal.equals(""))
										rabn.setKey(id_ab_normal);
									String table_name = rsic.getTableName();
									rabn.setEntity(table_name);
									java.util.Date date = new java.util.Date();
									rabn.setMessage(ok.getMessage());
									rabn.setMessageDate(new Timestamp(date.getTime()));
									anomalie.add(rabn);
								}
								
								// per mentenere le anomalie in memoria ... tengo solo le ultime 1000
								if (anomalie.size()>1000) {
									anomalie.remove(0);
								}
								
								// TOLTO IL BREAK - CREDO NON SERVA  - OGNI COMMAND POTREBE AVERE DELLE SUE ANOMALIE break;
							}
							
						} finally {
							ctx1=null;
						}
					}
	
				} catch (Exception e)
				{
					log.error("Eccezione nel trattamento di un record", e);
					CommandAck cmd = new ErrorAck(e.getMessage());
					return (cmd);
				}

			}
			log.debug("NUMERO ANOMALIE = " + anomalie.size());
			if (anomalie.size() > 0)
			{
				CommandAck ritorno = new ApplicationAck("Presenti Anomalie di Elaborazione - riportate solo le ultime 100 riscontrate");
				((ApplicationAck) ritorno).setAbn(anomalie);
				return (ritorno);
			}
			return new ApplicationAck("Records Elaborati:" + conteggio + " Anomalie di Elaborazione:" + anomalie.size());
		}
		catch (Exception e)
		{
			log.error("Eccezione su Iterazione del resultset", e);
			return new ErrorAck(e);
		} finally {
			if (rs!=null)
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					log.error("Non si è riuscito a chiudere il resultset nel ResultSetIterator",e);
				}
		} 
	}
}
