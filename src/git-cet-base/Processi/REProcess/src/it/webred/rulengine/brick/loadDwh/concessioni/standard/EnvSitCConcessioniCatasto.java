package it.webred.rulengine.brick.loadDwh.concessioni.standard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;
import it.webred.rulengine.db.model.RAbNormal;

public class EnvSitCConcessioniCatasto extends EnvInsertDwh {



	public EnvSitCConcessioniCatasto(String nomeTabellaOrigine, String...  nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		
		try {
				LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
				ret.add(params);
		      String foglio = rs.getString("foglio");
		      String particella = rs.getString("particella");
		      String subalterno  = rs.getString("subalterno");
		 		  params.put("ID_ORIG", null);
		 		  params.put("PROVENIENZA","C");
		 		  params.put("FK_ENTE_SORGENTE", 3);
			      params.put("ID_ORIG_C_CONCESSIONI",rs.getString("CHIAVE_RELAZIONE") );
				  params.put("FOGLIO", foglio);
				  params.put("PARTICELLA", particella);
				  params.put("SUBALTERNO", subalterno);
				  params.put("TIPO",rs.getString("TIPO") );
				  params.put("SEZIONE", rs.getString("SEZIONE"));

				  params.put("DT_EXP_DATO", (Timestamp) altriParams[0]  ); 
					params.put("DT_INI_VAL_DATO", null);
					params.put("DT_FINE_VAL_DATO", null);
					params.put("FLAG_DT_VAL_DATO", 0);
					
		} finally
		{
			
		}
				
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
