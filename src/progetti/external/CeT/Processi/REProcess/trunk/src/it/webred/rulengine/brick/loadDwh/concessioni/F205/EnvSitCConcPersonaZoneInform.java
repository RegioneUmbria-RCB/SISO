package it.webred.rulengine.brick.loadDwh.concessioni.F205;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


public class EnvSitCConcPersonaZoneInform extends EnvInsertDwh {



	public EnvSitCConcPersonaZoneInform(String nomeTabellaOrigine, String... nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		
		LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
		ret.add(params);
		  params.put("ID_ORIG", rs.getString("PK_CONC_PER"));
		  params.put("PROVENIENZA","I");
		  params.put("FK_ENTE_SORGENTE", 3);
	      params.put("ID_ORIG_C_PERSONA",rs.getString("FK_PERSONA") );
		  String titolo = null;
		  if ("1".equals(rs.getString("TIPO_SOGGETTO")))
				  titolo = "Proprietario";
		  if ("2".equals(rs.getString("TIPO_SOGGETTO")))
				  titolo = "Richiedente";
		  
	      params.put("TITOLO", titolo);
	      params.put("ID_ORIG_C_CONCESSIONI", rs.getString("FK_CONC") );
			params.put("DT_EXP_DATO", (Timestamp) altriParams[0]  ); 
			params.put("DT_INI_VAL_DATO", null);
			params.put("DT_FINE_VAL_DATO", null);
			params.put("FLAG_DT_VAL_DATO", 0);
		
		
		
				
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
