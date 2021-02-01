package it.webred.rulengine.brick.loadDwh.load.concessioni.v2;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;
import it.webred.rulengine.db.model.RAbNormal;

public class EnvSitCConcessioniCatasto extends it.webred.rulengine.brick.loadDwh.load.concessioni.v1.EnvSitCConcessioniCatasto {


	public EnvSitCConcessioniCatasto(String nomeTabellaOrigine, String provenienza, String...  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRighe(ResultSet rs) throws Exception {
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		try {
				LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
		      String foglio = rs.getString("foglio");
		      String particella = rs.getString("particella");
		      String subalterno  = rs.getString("subalterno");
		 		  params.put("ID_ORIG", rs.getString("CHIAVE"));
		 		  params.put("PROVENIENZA",getProvenienza());
		 		  params.put("FK_ENTE_SORGENTE", 3);
			      params.put("ID_ORIG_C_CONCESSIONI",rs.getString("CHIAVE_RELAZIONE") );
				  params.put("FOGLIO", foglio);
				  params.put("PARTICELLA", particella);
				  params.put("SUBALTERNO", subalterno);
				  params.put("TIPO",rs.getString("TIPO") );
				  params.put("SEZIONE", rs.getString("SEZIONE"));
				  params.put("CODICE_FABBRICATO", rs.getString("CODICE_FABBRICATO"));
				  params.put("ID_ORIG_C_CONC_INDIRIZZI", rs.getString("CHIAVE_RELAZIONE_E"));

				  params.put("DT_EXP_DATO", (Timestamp) altriParams[0]  ); 
					params.put("DT_INI_VAL_DATO", null);
					params.put("DT_FINE_VAL_DATO", null);
					params.put("FLAG_DT_VAL_DATO", 0);

					ret.add(new RigaToInsert(params));

		} finally
		{
			
		}
				
		return ret;
	}










}
