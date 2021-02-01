package it.webred.rulengine.brick.loadDwh.concessioni.F704;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitCConcPersona extends EnvInsertDwh {
	
	public EnvSitCConcPersona(String nomeTabellaOrigine, String...  nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}
	
public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		
		ArrayList<LinkedHashMap <String, Object>> ret = new ArrayList<LinkedHashMap<String,Object>>();
		
		if (rs.getObject("A") != null && !rs.getString("A").trim().equals("")) {
			
			LinkedHashMap<String, Object> params = null;
			
			String richied = rs.getObject("E") != null ? rs.getString("E").trim() : "";
			String indRichied = rs.getObject("F") != null ? rs.getString("F").trim() : "";
			String progett = rs.getObject("K") != null ? rs.getString("K").trim() : "";
			String dirLav = rs.getObject("L") != null ? rs.getString("L").trim() : "";
			String impresa = rs.getObject("M") != null ? rs.getString("M").trim() : "";
			int indice = 1;
			
			if (!richied.equals("")) {
				params = new LinkedHashMap<String, Object>();
				
				params.put("ID_ORIG", null);
		 		params.put("PROVENIENZA", "C");
		 		params.put("FK_ENTE_SORGENTE", 3);
			    params.put("ID_ORIG_C_PERSONA", indice + "@" + rs.getString("A"));
				params.put("TITOLO", "Richiedente");
			    params.put("ID_ORIG_C_CONCESSIONI", rs.getString("A"));
			    params.put("DT_EXP_DATO", (Timestamp) altriParams[0]); 
			    params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);
				
				ret.add(params);
				indice++;
			}
			
			if (!progett.equals("")) {
				params = new LinkedHashMap<String, Object>();
				
				params.put("ID_ORIG", null);
		 		params.put("PROVENIENZA", "C");
		 		params.put("FK_ENTE_SORGENTE", 3);
			    params.put("ID_ORIG_C_PERSONA", indice + "@" + rs.getString("A"));
				params.put("TITOLO", "Progettista");
			    params.put("ID_ORIG_C_CONCESSIONI", rs.getString("A"));
			    params.put("DT_EXP_DATO", (Timestamp) altriParams[0]); 
			    params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);

				ret.add(params);
				indice++;
			}
			
			if (!dirLav.equals("")) {
				params = new LinkedHashMap<String, Object>();
				
				params.put("ID_ORIG", null);
		 		params.put("PROVENIENZA", "C");
		 		params.put("FK_ENTE_SORGENTE", 3);
			    params.put("ID_ORIG_C_PERSONA", indice + "@" + rs.getString("A"));
				params.put("TITOLO", "Direttore Lavori");
			    params.put("ID_ORIG_C_CONCESSIONI", rs.getString("A"));
			    params.put("DT_EXP_DATO", (Timestamp) altriParams[0]); 
			    params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);

				ret.add(params);
				indice++;
			}
			
			if (!impresa.equals("")) {
				params = new LinkedHashMap<String, Object>();
				
				params.put("ID_ORIG", null);
		 		params.put("PROVENIENZA", "C");
		 		params.put("FK_ENTE_SORGENTE", 3);
			    params.put("ID_ORIG_C_PERSONA", indice + "@" + rs.getString("A"));
				params.put("TITOLO", "Impresa");
			    params.put("ID_ORIG_C_CONCESSIONI", rs.getString("A"));
			    params.put("DT_EXP_DATO", (Timestamp) altriParams[0]); 
			    params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);

				ret.add(params);
				indice++;
			}
		}
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return null;
		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
