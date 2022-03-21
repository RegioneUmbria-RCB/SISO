package it.webred.rulengine.brick.superc.importDati;

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
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.impl.bean.GenericTuples.T1;
import it.webred.rulengine.impl.bean.GenericTuples.T2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class ImportDatiDb extends Command {

	


	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportDatiDb.class.getName());
	protected static final String OK = "Elaborazione Terminata Correttamente";
	protected Connection con = null;
	protected String processId ; 
	protected Timestamp dataExport;
	protected Context ctx;
	protected List<RAbNormal> abnormals = new ArrayList<RAbNormal>();
	private CurrentItem currentItem ; 

	
	public ImportDatiDb(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}
	

	protected abstract void processingRow(ResultSet rs) throws RulEngineException;
	protected abstract void preProcesing() throws RulEngineException;
	protected abstract void preProcesingRow(ResultSet rs) throws RulEngineException;
	protected abstract CommandAck execute(Context ctx, Connection conn) throws CommandException;
	


	
	public CommandAck run(Context ctx)	throws CommandException {
		
		Connection conn = ctx.getConnection((String)ctx.get("connessione"));
		CommandAck ok = execute(ctx, conn);
		
		boolean anomalievf = false;
		if (ok instanceof RejectAck || ok instanceof ErrorAck)
			return ok;
		if (ok instanceof ApplicationAck)
		{
			if (abnormals.size() > 0)
				anomalievf = true;
		}
		
		
		if (anomalievf)
		{
			CommandAck ritorno = new ApplicationAck("Presenti Anomalie di Elaborazione");
			((ApplicationAck) ritorno).setAbn(abnormals);
			return (ritorno);
		}
         
		
		return new ApplicationAck("Elaborazione terminata correttamente");
		
	}
	
	protected String elabora(Connection conn, Context ctx , ResultSet rs,  CurrentItem itemInElaborazione)  throws Exception {
		try {
			this.processId = ctx.getProcessID();
			this.con = conn;
			this.dataExport = new Timestamp(new java.util.Date().getTime());
			this.ctx = ctx;
			this.currentItem = itemInElaborazione;
			
			preProcesing();
	
			while (rs.next()) {
				preProcesingRow(rs);
				processingRow(rs);
				
			}
			
			
		} finally  {
		}

		return OK;
	}
	
	
	protected CurrentItem getCurrentItem() {
		return currentItem;
	}


	
	
	
	
	
}
