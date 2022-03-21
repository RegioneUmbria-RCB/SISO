package it.webred.rulengine.brick.loadDwh.concessioni.F704;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitCConcIndirizzi extends EnvInsertDwh {
	
	public EnvSitCConcIndirizzi(String nomeTabellaOrigine, String...  nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		
		ArrayList<LinkedHashMap <String, Object>> ret = new ArrayList<LinkedHashMap<String,Object>>();
		
		if (rs.getObject("A") != null && !rs.getString("A").trim().equals("") &&
			rs.getObject("H") != null && !rs.getString("H").trim().equals("")) {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			
			//n.b. troppe casistiche differenti nel campo H per poter scindere indirizzo e civico;
			//il sedime manca;
			//si deve per forza mettere tutto il valore del campo H nel campo INDIRIZZO di SIT_C_CONC_INDIRIZZI
			params.put("ID_ORIG", null);
	 		params.put("PROVENIENZA", "C");
	 		params.put("FK_ENTE_SORGENTE", 3);
		    params.put("INDIRIZZO", rs.getString("H"));
		    params.put("CIVICO_COMPOSTO", null);
		    params.put("CIVICO", null);
			params.put("SEDIME", null);
			params.put("CODICE_VIA", null);
		    params.put("ID_ORIG_C_CONCESSIONI", rs.getString("A"));
		    params.put("ID_ORIG_C_CONC_INDIRIZZI", null);
			params.put("DT_EXP_DATO", (Timestamp) altriParams[0]); 
			params.put("DT_INI_VAL_DATO", null);
			params.put("DT_FINE_VAL_DATO", null);
			params.put("FLAG_DT_VAL_DATO", 0);

			ret.add(params);
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
