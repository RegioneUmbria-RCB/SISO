package it.webred.rulengine.diagnostics.utils;

import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;
import it.webred.rulengine.diagnostics.bean.ListDiagnostics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class ChkDiagnostics {
	
	protected static org.apache.log4j.Logger log = Logger.getLogger(ChkDiagnostics.class.getName());
	
	public static ListDiagnostics VerificaEsecuzione(Connection conn, String codCommand) throws Exception{
		log.info("[VERIFICAESECUZIONE]  - START ");
		ListDiagnostics listaDia = new ListDiagnostics();
		DiagnosticConfigBean diaConf = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
						
			sql = "SELECT * FROM DIA_CATALOGO WHERE (fk_cod_command_group = ? or fk_cod_command = ?) and abilitata=1 ORDER BY IDCATALOGODIA";
			log.debug("[VERIFICAESECUZIONE] - Recupero id diagnostiche sql " + sql);
			log.debug("[VERIFICAESECUZIONE] - codice comando " + codCommand);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, codCommand);
			pstmt.setString(2, codCommand);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("IDCATALOGODIA");
				log.debug("[VERIFICAESECUZIONE] - IDCATALOGODIA recuperato:" + id );
				diaConf = new DiagnosticConfigBean(id);												
				verificaPropConfig(id, diaConf);
				log.debug("[VERIFICAESECUZIONE] - Obj DiagnosticConfigBean : " + diaConf.toString());
				listaDia.addDiagnostica(diaConf);
			}
								
			return listaDia;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				log.error("[VERIFICAESECUZIONE] - Errore: "+ e.getMessage());				
			}
			log.info("[VERIFICAESECUZIONE]  - END ");
		}		
	}
	
	private static void verificaPropConfig(int idCatalogoDia, DiagnosticConfigBean diaConfig) throws Exception {
						
		try {
			
			//controllo se nel file xml delle diagnostiche il nodo per questo id è stato inserito
			XMLManager.getSingletonObject().getDiagnosticConfig(idCatalogoDia,diaConfig);
			if (!diaConfig.isValorizzata()) {
				diaConfig.setExecute(false);
				log.debug("[VERIFICAPROPCONFIG] - Impostato EXECUTE = False iddia " + idCatalogoDia );
				return;
			}											
			
		} catch (Exception e) {
			throw e;
		} finally {
					
		}					
	}	
}
