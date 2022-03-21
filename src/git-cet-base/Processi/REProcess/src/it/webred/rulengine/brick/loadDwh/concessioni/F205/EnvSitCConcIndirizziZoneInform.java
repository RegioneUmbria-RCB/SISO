package it.webred.rulengine.brick.loadDwh.concessioni.F205;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.def.TypeFactory;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class EnvSitCConcIndirizziZoneInform extends EnvInsertDwh {



	public EnvSitCConcIndirizziZoneInform(String nomeTabellaOrigine, String... nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		

		
			LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
			ret.add(params);

			String civico = rs.getString("CIVICO");
			String scala = rs.getString("SCALA");
			String piano = rs.getString("PIANO");
			
			String xmlCivicoComplex = "<complexParam>"   
			+"<param type='java.lang.String' name='numero'>"+civico+"</param>" 
			+ "<param type='java.lang.String' name='scala'>"+scala+"</param>"
			+ "<param type='java.lang.String' name='scala'>"+piano+"</param>"
			+ "</complexParam>";
			ComplexParam civicoComposto = null;			
			try {
		    	  civicoComposto = (ComplexParam)TypeFactory.getType(xmlCivicoComplex, "it.webred.rulengine.type.ComplexParam");
		      } catch (Exception e) {
		    	  try {
		    		  log.debug("ERRORE NELLA CREAZIONE DEL COMPLEXPARAM E TENTATIVO DI CONVERSIONE UTF-8 PER IL CIVICO: " + civico + "/" + scala + "/" + piano);
		    		  xmlCivicoComplex = "<complexParam>"   
		    					+"<param type='java.lang.String' name='numero'>"+ComplexParam.cleanParam(new String(Charset.forName("UTF-8").encode(civico).array()))+"</param>" 
		    					+ "<param type='java.lang.String' name='scala'>"+ComplexParam.cleanParam(new String(Charset.forName("UTF-8").encode(scala).array()))+"</param>"
		    					+ "<param type='java.lang.String' name='scala'>"+ComplexParam.cleanParam(new String(Charset.forName("UTF-8").encode(piano).array()))+"</param>"
		    					+ "</complexParam>";
		    		  civicoComposto = (ComplexParam)TypeFactory.getType(xmlCivicoComplex, "it.webred.rulengine.type.ComplexParam");
		    	  } catch (Exception e1) {
		    		  log.error("ERRORE NELLA CREAZIONE DEL COMPLEXPARAM PER IL CIVICO: " + civico + "/" + scala + "/" + piano + " - " + e1.getMessage());
		    		  throw e1;
				  }
		      }
	      
	 		  params.put("ID_ORIG", rs.getString("PK_OGGETTO"));
	 		  params.put("PROVENIENZA","I");
	 		  params.put("FK_ENTE_SORGENTE", 3);
		      params.put("INDIRIZZO",rs.getString("INDIRIZZO") );
		      if(civicoComposto!=null)
		    	  params.put("CIVICO_COMPOSTO",civicoComposto );
		      params.put("CIVICO", civico);
			  params.put("SEDIME", null);
			  params.put("CODICE_VIA", rs.getString("CODICE_VIA"));
		      params.put("ID_ORIG_C_CONCESSIONI",rs.getString("FK_CONC") );
		      params.put("ID_ORIG_C_CONC_INDIRIZZI", rs.getString("PK_OGGETTO") );
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
		
		
	}








}
