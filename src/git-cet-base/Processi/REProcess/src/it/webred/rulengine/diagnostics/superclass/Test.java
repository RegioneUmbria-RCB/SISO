package it.webred.rulengine.diagnostics.superclass;

import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
//		Connection connDati = null;
//		Connection connConf = null;
//		Statement st = null;
//		ResultSet rs = null;
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
//			String DB_URL="jdbc:oracle:thin:@roma:1521:DBCAT";			
//			connDati = DriverManager.getConnection (DB_URL,"DIOGENE_DEV","DIOGENE_DEV");			
//			connConf = DriverManager.getConnection (DB_URL,"RE_DEV","RE_DEV");
//			
//			ChkDiagnostics chkDia = new ChkDiagnostics();
//			ListDiagnostics listDia = chkDia.VerificaEsecuzione(connConf,null,new int[]{1},"D704");
//							
//			for (DiagnosticConfigBean diaConf : listDia.getDiagnostics()) {
//				if (diaConf.isExecute()){
//					System.out.println("ESEGUO Diagnostica : " + diaConf.toString());
//					
//					ElaboraDiagnostics elab = new ElaboraDiagnostics(connConf,connDati,diaConf);
//					elab.setProcessId("PROCESSID");			
//					elab.ExecuteDiagnostic();
//					
//					System.out.println("Diagnostica Eseguita correttamente");
//				}else{
//					System.out.println("NON ESEGUO Diagnostica : " + diaConf.toString());
//				}
//			}

//			st = connDati.createStatement();
//			rs = st.executeQuery("SELECT * FROM DIA_TESTATA");
//			while (rs.next()) {
//				System.out.println(rs.getInt("ID"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (st != null)
//				st.close();
//			if (rs != null)
//				rs.close();
//			if (connDati != null) {
//				try {
//					connDati.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}	
//			if (connConf != null) {
//				try {
//					connConf.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}

	}

}
