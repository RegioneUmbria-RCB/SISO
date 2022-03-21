package it.webred.rulengine.brick.core;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/*
 * 
 * @author DAN
 * @version $Revision: 1.4 $ $Date: 2008/10/31 09:43:17 $
 */
public class EseguiCaricamentoDWHDocfa extends Command  implements Rule {
	private static final Logger log = Logger.getLogger(EseguiCaricamentoDWHDocfa.class.getName());
	
	public EseguiCaricamentoDWHDocfa(BeanCommand bc) {
		super(bc);
	}
	
	public EseguiCaricamentoDWHDocfa(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	public CommandAck run(Context ctx)	{
		Connection conn = null;
		Statement statment = null;
		String sqlControlloSemaforo = "";
		
		//String tabelleCaronte = "DOC_DOCFA_1_0,DM_DOCFA_1_0,DC_DOCFA_1_0,PL_DOCFA_1_0";
		String tabelleCaronte = _jrulecfg.getProperty("rengine.rule.dwh.tab");
		
		//recupero comandi sql da file cfg
		//String[] sqls = {sql1,sql2,sql3,sql4,sql5,sql6,sql7,sql8,sql9,sql10,sql11,sql16,sql17,sql12,sql13,sql14,sql15};
		String[] sqls = {_jrulecfg.getProperty("rengine.rule.sql.1.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.2.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.3.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.4.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.5.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.6.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.7.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.8.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.9.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.10.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.11.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.16.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.17.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.12.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.13.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.14.value"),
						 _jrulecfg.getProperty("rengine.rule.sql.15.value")};

		boolean semaforoRosso = false;
		
		try	{
			log.debug("Recupero parametro da contesto");
			conn = ctx.getConnection((String)ctx.get("connessione"));
			
			String tabCar[]=tabelleCaronte.split(",");

		    statment = conn.createStatement();
			
		    for (int i = 0; i < tabCar.length; i++) {
					sqlControlloSemaforo += "or NOME_TAB='"+tabCar[i]+"' ";
			}
		    
		    sqlControlloSemaforo = sqlControlloSemaforo.substring("or ".length());
		    //attendeo semafori verdi per tutte le tabelle
			log.debug("controllaSemaforo");

		    //controllaSemaforo(statment,"select NOME_TAB, FLAG_SEMAFORO from semaforo where FLAG_SEMAFORO = 1 AND ("+sqlControlloSemaforo+")");
			controllaSemaforo(statment,(_jrulecfg.getProperty("rengine.rule.sql.semaforo.check")).replace("$CONTROLLO_SEMAFORO", sqlControlloSemaforo));
		    statment.cancel();
		    
		    //statment.executeUpdate("UPDATE semaforo set FLAG_SEMAFORO = 1 WHERE "+sqlControlloSemaforo);
		    statment.executeUpdate((_jrulecfg.getProperty("rengine.rule.sql.semaforo.on")).replace("$CONTROLLO_SEMAFORO", sqlControlloSemaforo));
		    
		    conn.commit();
		    semaforoRosso = true;
			log.debug("semaforoRosso");
			
			//esegu aggiornamenti da sql in param
		    List<String> numeroUpdate = new LinkedList<String>();
			for (int i = 0; i < sqls.length; i++)
			{
				if (!sqls[i].replace("\t", "").trim().equals(""))
				{
					statment.cancel();
					String nometabellaInsert = "(non riconosciuta)";

					try
					{
						if(sqls[i].toUpperCase().indexOf(" INTO ") > 0)
						{
							nometabellaInsert = (sqls[i].substring(sqls[i].toUpperCase().indexOf(" INTO ")+5)).trim();
							int idxFineNomeTab = nometabellaInsert.indexOf(" ")<nometabellaInsert.indexOf("(")?nometabellaInsert.indexOf(" "):nometabellaInsert.indexOf("(");
							nometabellaInsert = nometabellaInsert.substring(0,idxFineNomeTab-2);
						}
						else if(sqls[i].toUpperCase().indexOf("UPDATE ")> -1)
						{
							nometabellaInsert = (sqls[i].substring(sqls[i].toUpperCase().indexOf("UPDATE ")+7)).trim();
							nometabellaInsert = nometabellaInsert.substring(0,nometabellaInsert.indexOf(" "));
						}

						log.debug("Nome tabella Insert:" + nometabellaInsert);
						
					}catch(Exception nonric){}
					
					log.debug("Esecuzione: \n"+sqls[i]+" \n ");

					numeroUpdate.add("Aggiornati "+ statment.executeUpdate(sqls[i])+" record in tabella "+nometabellaInsert+" \n") ;					
				}
			}
			
			Iterator it = numeroUpdate.iterator();
			String risNumRecord ="";
			while (it.hasNext())
				risNumRecord+= " "+(String) it.next();
				
			
			return (new ApplicationAck("Caricamento da sql eseguito correttamente. Dettaglio record inseriti:\n "+risNumRecord));
			
		}
		catch(Exception ex)
		{
			try
			{
				conn.rollback();
			}
			catch (SQLException e)
			{
			}
			
			log.error("Errore Esegui Caricamenti Sql", ex);
			ErrorAck ea = new ErrorAck(ex.getMessage());
			return (ea);
		}
		finally
		{
			try
			{
				if (statment != null && semaforoRosso && !sqlControlloSemaforo.equals(""))
				{
					statment.cancel();
					statment.executeUpdate((_jrulecfg.getProperty("rengine.rule.sql.semaforo.off")).replace("$CONTROLLO_SEMAFORO", sqlControlloSemaforo));
					conn.commit();
				}
				if (statment != null)
					statment.close();

			}
			catch (Exception e)
			{
			}
		}
	}

	

	private void controllaSemaforo(Statement statmentSem,String sqlControlloSemaforo) throws Exception	{
		log.debug(sqlControlloSemaforo);
		ResultSet rset = statmentSem.executeQuery(sqlControlloSemaforo);
		if (rset.next())
		{
			log.debug("Trovato lock in semaforo su tabella : "+rset.getString("NOME_TAB")+" riprovo tra un minuto");
			synchronized (this)
			{				
				wait(1 * 1000 * 60);
				controllaSemaforo(statmentSem,sqlControlloSemaforo);
			}
		}
		
	}
	
}
