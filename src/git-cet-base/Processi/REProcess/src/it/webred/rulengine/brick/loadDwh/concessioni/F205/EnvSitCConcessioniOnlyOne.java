package it.webred.rulengine.brick.loadDwh.concessioni.F205;


import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class EnvSitCConcessioniOnlyOne extends EnvInsertDwh {



	public EnvSitCConcessioniOnlyOne(String nomeTabellaOrigine, String... nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		

	LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
		
		params.put("ID_ORIG", rs.getString("A"));
		
		String concNnum = rs.getString("G1")==null?"":"N." + rs.getString("G1");
		
		params.put("FK_ENTE_SORGENTE", 3);
		params.put("CONCESSIONE_NUMERO",  concNnum);
		params.put("PROGRESSIVO_NUMERO", rs.getString("B"));
		params.put("PROGRESSIVO_ANNO", rs.getString("C"));
		params.put("PROTOCOLLO_DATA", rs.getTimestamp("D")); // DATARICHIESTA 
		params.put("PROTOCOLLO_NUMERO", rs.getString("E"));
		//modificati Filippo Mazzini 20/09/2013 per correzione errore di caricamento (ved. Mantis 441)
		params.put("TIPO_INTERVENTO", rs.getString("H")==null?null:rs.getString("H").trim());
		params.put("OGGETTO", rs.getString("F")==null?null:rs.getString("F").trim());
		/*params.put("TIPO_INTERVENTO", rs.getString("O"));
		params.put("OGGETTO", rs.getString("H")==null?null:rs.getString("H").trim());*/
		params.put("PROCEDIMENTO", rs.getString("G"));
		
		
		params.put("ZONA", rs.getString("Z"));
		params.put("DATA_RILASCIO",rs.getTimestamp("H1")); // DATA DI RILASCIO DELLA CONCESSIONE (NON PRESENTE PER DIA)
		// NON TRATTATO IN DWH  : DATA CHIUURA PROICEDIMENTO
		// params.put("DATA_CHIUSURA", rs.getTimestamp("B1")); // la data di chiusura del procedimento
		params.put("DATA_INIZIO_LAVORI", rs.getTimestamp("A1"));
		params.put("DATA_FINE_LAVORI", rs.getTimestamp("F1"));
			
		
		
		params.put("PROVENIENZA", "OO");
		params.put("ESITO", rs.getString("C1"));

		params.put("DT_EXP_DATO", (Timestamp) altriParams[0] );
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);
		
		ret.add(params);
		
		
				
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE A=? AND DT_EXP_DATO=?";
		
	}





	@Override
	public void executeSqlPostInsertRecord(Connection conn, 
			LinkedHashMap<String, Object> currRecord) throws Exception {
		
		
		
		
	}








}
