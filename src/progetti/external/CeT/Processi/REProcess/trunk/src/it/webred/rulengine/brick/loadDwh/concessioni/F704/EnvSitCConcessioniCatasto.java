package it.webred.rulengine.brick.loadDwh.concessioni.F704;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitCConcessioniCatasto extends EnvInsertDwh {
	
	public EnvSitCConcessioniCatasto(String nomeTabellaOrigine, String...  nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		
		ArrayList<LinkedHashMap< String, Object>> ret = new ArrayList<LinkedHashMap<String,Object>>();
		
		if (rs.getObject("A") != null && !rs.getString("A").trim().equals("") && 
			rs.getObject("J") != null && !rs.getString("J").trim().equals("")) {			
			String catasto = rs.getString("J");
			String[] datiCatasto = catasto.split("/");
			if (datiCatasto.length == 2) {
				LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
				String particella = datiCatasto[0].trim();
				String foglio = datiCatasto[1].trim();
				
				params.put("ID_ORIG", null);
		 		params.put("PROVENIENZA", "C");
		 		params.put("FK_ENTE_SORGENTE", 3);
			    params.put("ID_ORIG_C_CONCESSIONI", rs.getString("A"));
				params.put("FOGLIO", foglio);
				params.put("PARTICELLA", particella);
				params.put("SUBALTERNO", null);
				params.put("TIPO", null);
				params.put("SEZIONE", null);
				params.put("DESCRIZIONE", null);
				params.put("DT_EXP_DATO", (Timestamp) altriParams[0]); 
				params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);
				
				ret.add(params);
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

