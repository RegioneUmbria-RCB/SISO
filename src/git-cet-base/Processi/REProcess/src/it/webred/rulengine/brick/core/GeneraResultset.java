package it.webred.rulengine.brick.core;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;

import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.impl.TypeFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;

import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;

import com.sun.rowset.CachedRowSetImpl;

/**
 * Regola che in base ad una stringa SQL genera e inserisce nel contesto un
 * java.sql.ResultSet. Se il resultset è stato generato la regola ritorna un CommandAck
 * con un messaggio di conferma, se invece non è stato possibile creare un resultset
 * per quella stringa, la regola ritorna un ErrorAck con il messaggio di errore.
 *
 * La cfg della regola è su file properties
 * 
 * @author sisani
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:24 $
 */
public class GeneraResultset extends Command implements Rule
{
	private static final Logger log = Logger.getLogger(GeneraResultset.class.getName());

	
	public GeneraResultset(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}


	@SuppressWarnings("unchecked")
	@Override
	public CommandAck run(Context ctx)	throws CommandException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			log.debug("Recupero parametro da contesto");
			conn = ctx.getConnection((String)ctx.get("connessione"));
			
			String sql = null;
			ComplexParam paramSql = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			
			HashMap<String,ComplexParamP> pars2 = paramSql.getParams();
			Set set2 = pars2.entrySet();
			Iterator it2 = set2.iterator();
			int i2 =1;
			while (it2.hasNext()) {
				Entry entry = (Entry)it2.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				sql = o.toString();
			}
			
			log.debug("Query SQL da eseguire:\n"+sql);
			
			log.debug("GeneraResultset ho ripreso i parametri");
			log.debug("Apro la connection");
			pstmt = conn.prepareStatement(sql);
			
			log.debug("Preparo il PreparedStatement");
			log.debug("ESECUZIONE QUERY");
			rs = pstmt.executeQuery();
			log.debug("FINE ESECUZIONE QUERY");
			
			//CachedRowSet crs = new CachedRowSetImpl();
			//crs.populate(rs);
			ctx.put(_jrulecfg.getProperty("rengine.rule.param.out.1.descr"), rs);
			
			log.debug("Ho messo il ResultSet nel contesto");
						
			return (new ApplicationAck("RESULTSET GENERATO CON SUCCESSO"));
		}
		catch (Exception e)
		{
			try
			{
				if (rs != null)					
					rs.close();
				
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception ec)
			{
				log.error("Errore chiusura resultSet o Statement!", ec);
			}
			log.error("Errore esecuzione GeneraResultset", e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

	}
}
