package it.webred.rulengine.brick.core;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/*
 * 
 * @author DAN
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:24 $
 */
public class EseguiCaricamentiSqlConSemaforo extends Command  implements Rule
{
	private static final Logger log = Logger.getLogger(EseguiCaricamentiSqlConSemaforo.class.getName());
	/**
	 * @param bc
	 */
	public EseguiCaricamentiSqlConSemaforo(BeanCommand bc)
	{
		super(bc);
	}
	
	
	public EseguiCaricamentiSqlConSemaforo(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}


	public CommandAck run(Context ctx)	{
		
		Connection conn = null;
		Statement statment = null;
		String sqlControlloSemaforo = "";
		boolean semaforoRosso = false;
		
		log.debug("Recupero parametro da contesto");
		conn = ctx.getConnection((String)ctx.get("connessione"));
		
		try	{
			
			String sql = null;
			ComplexParam param1 = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			
			HashMap<String,ComplexParamP> pars1 = param1.getParams();
			Set set1 = pars1.entrySet();
			Iterator it1 = set1.iterator();
			int i1 =1;
			while (it1.hasNext()) {
				Entry entry = (Entry)it1.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				sql = o.toString();
			}
			
			log.debug("Comandi SQL da eseguire:\n"+sql);
			
			String tabelleCaronte = null;
			ComplexParam param2 = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			HashMap<String,ComplexParamP> pars2 = param2.getParams();
			Set set2 = pars2.entrySet();
			Iterator it2 = set2.iterator();
			int i2 =1;
			while (it2.hasNext()) {
				Entry entry = (Entry)it2.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				tabelleCaronte = o.toString();
			}
			
			log.debug("Tabelle per semaforo:\n"+tabelleCaronte);
			
			
			if(sql==null || sql.trim().equals(""))
				return new ErrorAck("Il parametro sql non puo essere vuoto");
			if(tabelleCaronte==null || tabelleCaronte.trim().equals(""))
				return new ErrorAck("Il parametro tabelle semaforo non puo essere vuoto");
		
			String sqls[] = sql.split(";");
			String tabCar[] = tabelleCaronte.split(",");

		    statment = conn.createStatement();
			
		    for (int i = 0; i < tabCar.length; i++)	{
		    	sqlControlloSemaforo += "or NOME_TAB='"+tabCar[i]+"' ";
			}
		    
		    sqlControlloSemaforo = sqlControlloSemaforo.substring("or ".length());
		    //attendeo semafori verdi per tutte le tabelle
		    //controllaSemaforo(statment,"select NOME_TAB, FLAG_SEMAFORO from semaforo where FLAG_SEMAFORO = 1 AND ("+sqlControlloSemaforo+")");
		    controllaSemaforo(statment,(_jrulecfg.getProperty("rengine.rule.sql.controlla.semaforo.value"))
		    										.replace("$SQL_CONTROLLO_SEMAFORO", sqlControlloSemaforo));
		    statment.cancel();
		    
		    //statment.executeUpdate("UPDATE semaforo set FLAG_SEMAFORO = 1 WHERE "+sqlControlloSemaforo);
		    statment.executeUpdate((_jrulecfg.getProperty("rengine.rule.sql.controlla.semaforo.on.value"))
		    										.replace("$SQL_CONTROLLO_SEMAFORO", sqlControlloSemaforo));
		    conn.commit();
		    semaforoRosso = true;
			
			//esegu aggiornamenti da sql in param
		    List<String> numeroUpdate= new LinkedList<String>();
			for (int i = 0; i < sqls.length; i++)
			{
				if (!sqls[i].replace("\t", "").trim().equals(""))
				{
					statment.cancel();
					String nometabellaInsert = "(non riconosciuta)";
					try
					{
						log.debug("[CURRENTE STMT] "+sqls[i]);
						if(sqls[i].toUpperCase().indexOf(" INTO ") > 0)
						{
							nometabellaInsert = (sqls[i].substring(sqls[i].toUpperCase().indexOf(" INTO ")+5)).trim();
							int idxFineNomeTab = nometabellaInsert.indexOf(" ")<nometabellaInsert.indexOf("(")?nometabellaInsert.indexOf(" "):nometabellaInsert.indexOf("(");
							nometabellaInsert = nometabellaInsert.substring(0,idxFineNomeTab);
						}
						else if(sqls[i].toUpperCase().indexOf("UPDATE ")>0)
						{
							nometabellaInsert = (sqls[i].substring(sqls[i].toUpperCase().indexOf("UPDATE ")+7)).trim();
							nometabellaInsert = nometabellaInsert.substring(0,nometabellaInsert.indexOf(" "));
						}
						else if(sqls[i].toUpperCase().indexOf("DELETE ")>0)
						{
							nometabellaInsert = (sqls[i].substring(sqls[i].toUpperCase().indexOf("DELETE ")+7)).trim();
							nometabellaInsert = (sqls[i].substring(sqls[i].toUpperCase().indexOf("FROM ")+5)).trim();
							nometabellaInsert = nometabellaInsert.substring(0,nometabellaInsert.indexOf(" "));
						}
					}catch(Exception nonric){
						//TODO:do nothing
					}
					
					numeroUpdate.add("Aggiornati "+ statment.executeUpdate(sqls[i])+" record in tabella "+nometabellaInsert+" \n") ;					
					log.debug("Eseguito: \n"+sqls[i]+" \n "+numeroUpdate.get(i));
				}
			}

			Iterator it = numeroUpdate.iterator();
			String risNumRecord ="";
			while (it.hasNext()){
				risNumRecord+= " "+(String) it.next();
			}
				
			return (new ApplicationAck("Caricamento da sql eseguito correttamente. Dettaglio record inseriti:\n "+risNumRecord));
		}
		catch(Exception ex)
		{
			try {
				conn.rollback();
			}
			catch (SQLException e)	{
				//TODO:do nothing
			}
			
			log.error("Errore Esegui Caricamenti Sql", ex);
			ErrorAck ea = new ErrorAck(ex.getMessage());
			return (ea);
		}
		finally
		{
			try		{
				
				if (statment != null && semaforoRosso && !sqlControlloSemaforo.equals("")) {
					statment.cancel();
					//statment.executeUpdate("UPDATE semaforo set FLAG_SEMAFORO = 0 WHERE "+sqlControlloSemaforo);
					statment.executeUpdate((_jrulecfg.getProperty("rengine.rule.sql.controlla.semaforo.off.value"))
															.replace("$SQL_CONTROLLO_SEMAFORO", sqlControlloSemaforo));
					conn.commit();
				}
				if (statment != null) {
					statment.close();
				}
			}catch (Exception e){
				//TODO: do nothing
			}
		}
		
	}

	private void controllaSemaforo(Statement statmentSem,String sqlControlloSemaforo) throws Exception	{
		
		ResultSet rset = statmentSem.executeQuery(sqlControlloSemaforo);
		if (rset.next())
		{
			log.debug("Trovato lock in semaforo su tabella : "+rset.getString("NOME_TAB")+" riprovo tra un minuto");
			synchronized (this)	{
				
				wait(1 * 1000 * 60);
				controllaSemaforo(statmentSem,sqlControlloSemaforo);
			}
		}
	}
	


}
