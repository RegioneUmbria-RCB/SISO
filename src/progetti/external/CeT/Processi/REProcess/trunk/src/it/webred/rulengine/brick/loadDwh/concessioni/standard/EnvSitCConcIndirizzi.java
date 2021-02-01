package it.webred.rulengine.brick.loadDwh.concessioni.standard;

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

public class EnvSitCConcIndirizzi extends EnvInsertDwh {



	public EnvSitCConcIndirizzi(String nomeTabellaOrigine, String... nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		
			LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
			ret.add(params);
	      String indirizzo = rs.getString("indirizzo");
	      String sedime = rs.getString("sedime");
	      String codVia = rs.getString("codice_via");
	      String civico = rs.getString("civico");
	      String civico2 = rs.getString("civico2");
	      String civico3 = rs.getString("civico3");


	      String xmlCivicoComplex = "<complexParam><param type='java.lang.String' name='numero'>"+civico+"</param>";
	      if (civico2!=null)
	    	  xmlCivicoComplex+="<param type='java.lang.String' name='civico2'>"+civico2+"</param>";
	      if (civico3!=null)
	    	  xmlCivicoComplex+="<param type='java.lang.String' name='civico3'>"+civico3+"</param>";
	      xmlCivicoComplex+="</complexParam>";
	      ComplexParam civicoComposto = null;
	      try {
		      civicoComposto = (ComplexParam)TypeFactory.getType(xmlCivicoComplex, "it.webred.rulengine.type.ComplexParam");
	      } catch (Exception e) {
	    	  String civicoLog = civico;
    		  if (civico2!=null)
    			  civicoLog+=("/" + civico2);
    	      if (civico3!=null)
    	    	  civicoLog+=("/" + civico3);
	    	  try {	    		  
	    		  log.debug("ERRORE NELLA CREAZIONE DEL COMPLEXPARAM E TENTATIVO DI CONVERSIONE UTF-8 PER IL CIVICO: " + civicoLog);
	    		  xmlCivicoComplex = "<complexParam><param type='java.lang.String' name='numero'>"+ComplexParam.cleanParam(new String(Charset.forName("UTF-8").encode(civico).array()))+"</param>";
	    		  if (civico2!=null)
	    	    	  xmlCivicoComplex+="<param type='java.lang.String' name='civico2'>"+ComplexParam.cleanParam(new String(Charset.forName("UTF-8").encode(civico2).array()))+"</param>";
	    	      if (civico3!=null)
	    	    	  xmlCivicoComplex+="<param type='java.lang.String' name='civico3'>"+ComplexParam.cleanParam(new String(Charset.forName("UTF-8").encode(civico3).array()))+"</param>";
	    	      xmlCivicoComplex+="</complexParam>";
	    		  civicoComposto = (ComplexParam)TypeFactory.getType(xmlCivicoComplex, "it.webred.rulengine.type.ComplexParam");
	    	  } catch (Exception e1) {
	    		  log.error("ERRORE NELLA CREAZIONE DEL COMPLEXPARAM PER IL CIVICO: " + civicoLog + " - " + e1.getMessage());
	    		  throw e1;
			  }
	      }
	      
	 		  params.put("ID_ORIG", null);
	 		  params.put("PROVENIENZA","C");
	 		  params.put("FK_ENTE_SORGENTE", 3);
		      params.put("INDIRIZZO",indirizzo );
		      if(civicoComposto!=null)
		    	  params.put("CIVICO_COMPOSTO",civicoComposto );
		      params.put("CIVICO", civico);
			  params.put("SEDIME", sedime);
			  params.put("CODICE_VIA", codVia);
		      params.put("ID_ORIG_C_CONCESSIONI",rs.getString("chiave_a") );
		      params.put("ID_ORIG_C_CONC_INDIRIZZI",null );
				params.put("DT_EXP_DATO", (Timestamp) altriParams[0]  ); 
				params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);
			
		
		
		
				
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE CHIAVE=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";
		
	}





	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
	/*	
		// 
		// praticamente storicizzo eventuali records già presente per la stessa concessione
		// in qesta tabella
		//
		PreparedStatement pse = null;
		try {
			String sql = "         UPDATE SIT_C_CONC_INDIRIZZI i  "
				 +" set dt_fine_val =? "
				 +" where dt_fine_val is null "
				 +" and exists  "
				 +" (  select 1 from sit_c_conc_indirizzi ii  , sit_c_concessioni c "
				 +"    where i.id_ext_c_concessioni = ii.id_ext_c_concessioni  "
				 +"    and c.ID_EXT = ii.ID_EXT_C_CONCESSIONI "
				 +"    and c.id_orig = ? "
				 +"    and ii.dt_inizio_val  = ?   "
				 +"    and i.dt_inizio_val < ii.dt_inizio_val "
				 +"  )";
			try {
				pse = conn.prepareStatement(sql);
				pse.setTimestamp(1, (Timestamp)currRecord.get("DT_EXP_DATO"));
				pse.setString(2, (String)currRecord.get("ID_ORIG_C_CONCESSIONI"));
				pse.setTimestamp(3, (Timestamp)currRecord.get("DT_EXP_DATO"));
				
				pse.execute();
				
			} finally {
				if(pse!=null)
					pse.close();
			}
		} finally {
			
		}
		*/
		
	}








}
