package it.webred.rulengine.brick.loadDwh.concessioni.F205;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitCConcessioniCatastoZoneInform extends EnvInsertDwh {



	public EnvSitCConcessioniCatastoZoneInform(String nomeTabellaOrigine, String...  nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		
		try {
		
				LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
				ret.add(params);

				  params.put("ID_ORIG", rs.getString("PK_OGGETTO"));
		 		  params.put("PROVENIENZA","I");
		 		  params.put("FK_ENTE_SORGENTE", 3);
			      params.put("ID_ORIG_C_CONCESSIONI",rs.getString("FK_CONC") );
				  params.put("FOGLIO", rs.getString("FOGLIO"));
				  params.put("PARTICELLA", rs.getString("PARTICELLA"));
				  params.put("SUBALTERNO", rs.getString("SUBALTERNO"));
				  params.put("TIPO", null);
				  params.put("SEZIONE", null);
				  String des = rs.getString("DESTINAZIONE_USO")!=null?"DESTINAZIONE USO":null;
				  if (des!=null)
					  des += rs.getString("TUTELA")!=null?" TUTELA":"";
				  else
					  des = rs.getString("TUTELA")!=null?"TUTELA":"";
					  
				  params.put("DESCRIZIONE", des);
			      params.put("ID_ORIG_C_CONC_INDIRIZZI",rs.getString("PK_OGGETTO") );

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
		return null;
		
	}











	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}







}
