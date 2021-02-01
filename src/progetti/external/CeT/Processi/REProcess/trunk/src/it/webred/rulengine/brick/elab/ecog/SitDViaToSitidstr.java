package it.webred.rulengine.brick.elab.ecog;

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
import it.webred.rulengine.type.def.TypeFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class SitDViaToSitidstr extends Command implements Rule  {
	
	private static final Logger log = Logger.getLogger(CiviciEcograficoToSiticivi.class.getName());
	private Connection conn=null; 
	private String enteID;
	
	private String SQL_DELETE_SITIDSTR;
	private String SQL_LOAD_SITIDSTR;
	
	public SitDViaToSitidstr(BeanCommand bc) {
		super(bc);
	}

	public SitDViaToSitidstr(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		log.debug("SitDViaToSitidstr run()");
		CommandAck retAck = null; 
		enteID= ctx.getBelfiore();
		log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		conn = ctx.getConnection((String)ctx.get("connessione"));
		
		
		
		PreparedStatement pst =null; ResultSet rs =null; Statement st =null;

		try {
			
			log.info("Database Username: " + conn.getMetaData().getUserName());
			
			this.getJellyParam(ctx);
			log.info("Query SQL_DELETE_SITIDSTR da eseguire:\n"+ SQL_DELETE_SITIDSTR);
			pst = conn.prepareStatement(SQL_DELETE_SITIDSTR);
			pst.executeUpdate();
			log.info("La tabella SITIDSTR è stata svuotata.");
			
			log.info("Query SQL_LOAD_SITIDSTR da eseguire:\n"+ SQL_LOAD_SITIDSTR);
			pst = conn.prepareStatement(SQL_LOAD_SITIDSTR);
			pst.executeUpdate();
			log.info("La tabella SITIDSTR è stata caricata con i dati provenienti da SIT_D_VIA");
			
			retAck = new ApplicationAck("ESECUZIONE OK");
			return retAck;
			
		}catch (SQLException e) {
			log.error("ERRORE SQL " + e, e);
			ErrorAck ea = new ErrorAck(e);
			return ea;
		}catch(Exception eg){
			log.error("ERRORE Generico " + eg, eg);
			ErrorAck ea = new ErrorAck(eg);
			return ea;
		}finally {
			try {
				DbUtils.close(rs);
				DbUtils.close(pst);
			}
			catch (SQLException sqle) {
				log.error("ERRORE CHIUSURA OGGETTI SQL", sqle);
			}
		}
		
	}
	
	private void getJellyParam(Context ctx) throws Exception {
		try {
			
			int index = 1;
			log.info("**************************************************************rengine.rule.param.in."+index+".descr");
			this.SQL_DELETE_SITIDSTR = getJellyParam(ctx, index++, "SQL_DELETE_SITIDSTR");
			this.SQL_LOAD_SITIDSTR = getJellyParam(ctx, index++, "SQL_LOAD_SITIDSTR");
			
		
		}catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw e;
		}
	}
	
	
	private String getJellyParam(Context ctx, int index, String desc) throws Exception{
		
		String variabile = "";
		
		log.info("rengine.rule.param.in."+index+".descr");
		
		ComplexParam paramSql= (ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in."+index+".descr"));
		
		HashMap<String,ComplexParamP> p = paramSql.getParams();
		Set set = p.entrySet();
		Iterator it = set.iterator();
		int i = 1;
		while (it.hasNext()) {
			Entry entry = (Entry)it.next();
			ComplexParamP cpp = (ComplexParamP)entry.getValue();
			Object o = TypeFactory.getType(cpp.getValore(),cpp.getType());
			variabile = o.toString();
		}
		
		log.info("Query "+desc+" da eseguire:"+ variabile);
		
		return variabile;
	}

}
