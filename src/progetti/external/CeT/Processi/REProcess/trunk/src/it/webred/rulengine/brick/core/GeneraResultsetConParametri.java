package it.webred.rulengine.brick.core;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.db.model.RRuleParamOut;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * Regola che in base ad una stringa esegue una query SQL.
 * Se il sql è stato esguito la regola ritorna unn resultset 
 * Invece se non è stato possibile eseguire il sql la regola ritorna un ErrorAck
 * con il messaggio di errore.
 * 
 * Possono essere passati dei parametri all'sql che verranno sostituiti ai ? del PreparedStatement
 *
 * @author marcoric
 * @version $Revision: 1.4 $ $Date: 2010/09/28 12:12:26 $
 */
public class GeneraResultsetConParametri extends Command implements Rule
{
	


	private static final Logger log = Logger.getLogger(GeneraResultsetConParametri.class.getName());

	public GeneraResultsetConParametri(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public CommandAck run(Context ctx) throws CommandException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try
		{
			log.debug("Recupero parametro da contesto");
			conn = ctx.getConnection((String)ctx.get("connessione"));
			
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
			
			pstmt = conn.prepareStatement(sql);
			
			
			//ComplexParam parametri = (ComplexParam) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			ComplexParam parametri = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			// setto eventuali parametri passati per l'sql
			if (parametri!=null) {
				if (parametri.getParams()!=null && parametri.getParams().size()>0) {
					HashMap<String,ComplexParamP> pars = parametri.getParams();
					Set set = pars.entrySet();
					Iterator it = set.iterator();
					int i =1;
					while (it.hasNext()) {
						Entry entry = (Entry)it.next();
						ComplexParamP p = (ComplexParamP)entry.getValue();
						Object o = TypeFactory.getType(p.getValore(),p.getType());
						pstmt.setObject(i,o);
						i++;
					}
				}
			}
			
			rs = pstmt.executeQuery();

			List parametriOut = this.getParametersOut(_jrulecfg);
			ctx.put(((RRuleParamOut) parametriOut.get(0)).getDescr(), rs);
			
			return (new ApplicationAck("RESULTSET CON PARAMETRI GENERATO CON SUCCESSO"));
		}
		catch (Exception e)
		{
			log.error("Errore esecuzione sql:" + sql, e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} finally {
				// NON CHIUDO NULLA perché IL RESULTSET POTREBBE SUCCESSIVAMENTE ESSERE ITERATO

		}
	}


}
