package it.webred.rulengine.diagnostics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.rulengine.Context;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;

public class TestDiaNonStandard extends ElaboraDiagnosticsNonStandard {

	public TestDiaNonStandard(Connection connPar, Context ctxPar,
			List<RRuleParamIn> paramsPar) {
		super(connPar, ctxPar, paramsPar);
		log = Logger.getLogger(TestDiaNonStandard.class.getName());		
	}
	
	@Override
	protected void ElaborazioneNonStandard(DiagnosticConfigBean diaConfig,
			long idTestata) throws Exception {
		// TODO Auto-generated method stub
		log.debug("[ElaborazioneNonStandard] - Invoke class TestDiaNonStandard ");
		String enteID= this.getCodBelfioreEnte();
		log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		String connectionName = "DWH_" +enteID; 
		Connection conn = this.getCtx().getConnection(connectionName);
		PreparedStatement pst =null; ResultSet rs =null; Statement st =null;
		long id = 0;
		try {
			if (conn== null)
			log.debug("NO CONN IN CTX....");
			else {
				String sqlTest ="SELECT * FROM UI_TIT_NON_RESIDENTI WHERE ID = 1"; 
				pst = conn.prepareStatement(sqlTest);
				rs = pst.executeQuery();
				while(rs.next()) {
					log.debug("RIGA IN ELAB. titolari + " + rs.getString("TITOLARI") );
					id++;
					String sqlIns="INSERT INTO DIA_DETTAGLIO_D_NONSTD_TEST VALUES(" + id +"," + idTestata + ",2,'00100',3)";
					log.debug("sqlIns: " + sqlIns);
					st = conn.createStatement();
					st.execute(sqlIns);
				}
			}	
		}catch (SQLException e) {
			log.error("ERRORE SQL " + e);
		}finally {
			DbUtils.close(rs);
			DbUtils.close(st);
			DbUtils.close(pst);
		}
		
		super.ElaborazioneNonStandard(diaConfig, idTestata);
		
	}
	
}
