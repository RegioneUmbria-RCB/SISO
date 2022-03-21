package it.webred.rulengine.diagnostics;

import it.webred.rulengine.Context;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;
import it.webred.rulengine.diagnostics.superclass.ElaboraDiagnostics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

public class ElaboraDiagnosticsNonStandard extends ElaboraDiagnostics {

	public ElaboraDiagnosticsNonStandard(){
		super();
		log = Logger.getLogger(ElaboraDiagnosticsNonStandard.class.getName());
	}
		
	public ElaboraDiagnosticsNonStandard(Connection connPar, Context ctxPar,
			List<RRuleParamIn> paramsPar) {
		super(connPar, ctxPar, paramsPar);
		log = Logger.getLogger(ElaboraDiagnosticsNonStandard.class.getName());		
	}
	
	public String getSelectSql(DiagnosticConfigBean diaConf) {		
		log.info("[getSelectSql] - Invoke ");
		if (diaConf.getTableNameDettaglio().isEmpty()) return "";
		StringBuilder sb = new StringBuilder("Select * From ");
		sb.append(diaConf.getTableNameDettaglio());
		if (!diaConf.getFieldTableDettaglioForFK().isEmpty()){
			sb.append(" Where ");
			sb.append(diaConf.getFieldTableDettaglioForFK());
			sb.append(" = ? ");
		}
		log.info("[getSelectSql] " + sb.toString());
		return sb.toString();
	}
	
	@Override
	protected Object getResult(DiagnosticConfigBean diaConf) throws Exception {		
		log.info("[getResult] - Invoke ");
		return datiDiaTestata4Upd;
		 
	}
		
	@Override
	protected int getRecordsCount(DiagnosticConfigBean diaConf, long idTestata) throws Exception {
		// TODO Auto-generated method stub
		super.getRecordsCount(diaConf,idTestata);
		log.info("[getRecordsCount] - Invoke ");
		
		int numRec = 0;
		StringBuilder sql = new StringBuilder("Select count(*) as num_rec From ( ");
		sql.append(getSelectSql(diaConf));
		sql.append(" )");
		log.info("[getRecordsCount] - sql: " + sql);
		log.info("[getRecordsCount] - sql.toString(): " + sql.toString());
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			
			pstmt = getConn().prepareStatement(sql.toString());
			log.info("[getRecordsCount] - statement prepared for sql:" + sql);
			if (!diaConf.getFieldTableDettaglioForFK().isEmpty()) {
				log.info("[getRecordsCount] setting idTestata in sql... valore idTestata: " + idTestata); 
				pstmt.setLong(1, idTestata);
			}
			rs = pstmt.executeQuery();
			if (rs.next()) numRec = rs.getInt(1);				
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();
		}
		log.debug("[getRecordsCount] - Num. Record :" + numRec);
		return numRec;
	}
	
	@Override
	protected void ElaborazioneNonStandard(DiagnosticConfigBean diaConfig,
			long idTestata) throws Exception {
		// TODO Auto-generated method stub
		log.debug("[ElaborazioneNonStandard] - Invoke");
		super.ElaborazioneNonStandard(diaConfig, idTestata);
	}
	
	
}
