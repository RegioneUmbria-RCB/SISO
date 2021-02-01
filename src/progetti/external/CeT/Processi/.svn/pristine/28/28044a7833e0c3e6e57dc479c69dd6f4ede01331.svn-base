package it.webred.rulengine.brick.loadDwh.concessioni.standard;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


public class EnvSitCConcPersona extends EnvInsertDwh {



	public EnvSitCConcPersona(String nomeTabellaOrigine, String... nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
		

 		  params.put("ID_ORIG", null);
 		  params.put("PROVENIENZA","C");
 		  params.put("FK_ENTE_SORGENTE", 3);
	      params.put("ID_ORIG_C_PERSONA",rs.getString("CHIAVE"));
		  params.put("TITOLO", rs.getString("TITOLO"));
	      params.put("ID_ORIG_C_CONCESSIONI",rs.getString("CHIAVE_RELAZIONE") );
			params.put("DT_EXP_DATO", (Timestamp) altriParams[0]  ); 
			params.put("DT_INI_VAL_DATO", null);
			params.put("DT_FINE_VAL_DATO", null);
			params.put("FLAG_DT_VAL_DATO", 0);
				
			ret.add(params);

			return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE CHIAVE=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";

		
	}





	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}






}
