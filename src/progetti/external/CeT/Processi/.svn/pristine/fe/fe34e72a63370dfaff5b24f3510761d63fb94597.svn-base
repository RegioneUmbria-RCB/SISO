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

public class EnvSitCConcIndirizziOnlyOne extends EnvInsertDwh {



	public EnvSitCConcIndirizziOnlyOne(String nomeTabellaOrigine, String... nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		

		List<String> indirizzi = new  ArrayList<String>();
		List<String> sedimi = new  ArrayList<String>();
		List<String> codiciVia = new  ArrayList<String>();
		List<String> civici = new  ArrayList<String>();
		String indirs = rs.getString("M");
		if (indirs!=null)
			indirizzi = Arrays.asList( indirs.split("\\+"));
		String sedimis = rs.getString("L");
		if (sedimis!=null)
			sedimi = Arrays.asList( sedimis.split("\\+"));
		String codiciVias = rs.getString("N");
		if (codiciVias!=null)
			codiciVia = Arrays.asList( codiciVias.split("\\+"));
		String civicis = rs.getString("O");
		if (civicis!=null)
			civici = Arrays.asList( civicis.split("\\+"));

		
		Iterator<String> itrInd = indirizzi.iterator();
		int indexCount=0;
	    while (itrInd.hasNext()) {
			LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
			ret.add(params);
	      String indirizzo = (String) itrInd.next();
	      String sedime = null;
	      try {
		      sedime = sedimi.get(indexCount);
	      } catch (Exception e) {
	    	  
	      }
	      String codVia = null;
	      try {
	    	  codVia = codiciVia.get(indexCount);
	      } catch (Exception e) {
	    	  
	      }	 
	      String civico = null;
	      try {
	    	  civico = civici.get(indexCount);
	      } catch (Exception e) {
	    	  
	      }

	      String xmlCivicoComplex = "<complexParam><param type='java.lang.String' name='numero'>"+civico+"</param></complexParam>";
	      ComplexParam civicoComposto = null;
	      try {	    	  
	    	  civicoComposto = (ComplexParam)TypeFactory.getType(xmlCivicoComplex, "it.webred.rulengine.type.ComplexParam");
	      } catch (Exception e) {
	    	  try {
	    		  log.debug("ERRORE NELLA CREAZIONE DEL COMPLEXPARAM E TENTATIVO DI CONVERSIONE UTF-8 PER IL CIVICO: " + civico);
	    		  xmlCivicoComplex = "<complexParam><param type='java.lang.String' name='numero'>"+ComplexParam.cleanParam(new String(Charset.forName("UTF-8").encode(civico).array()))+"</param></complexParam>";
	    		  civicoComposto = (ComplexParam)TypeFactory.getType(xmlCivicoComplex, "it.webred.rulengine.type.ComplexParam");
	    	  } catch (Exception e1) {
	    		  log.error("ERRORE NELLA CREAZIONE DEL COMPLEXPARAM PER IL CIVICO: " + civico + " - " + e1.getMessage());
	    		  throw e1;
			  }
	      }
 		  params.put("ID_ORIG", null);
 		  params.put("PROVENIENZA","OO");
 		  params.put("FK_ENTE_SORGENTE", 3);
	      params.put("INDIRIZZO",indirizzo );
	      if(civicoComposto!=null)
	    	  params.put("CIVICO_COMPOSTO",civicoComposto );
	      params.put("CIVICO", civico);
		  params.put("SEDIME", sedime);
		  params.put("CODICE_VIA", codVia);
	      params.put("ID_ORIG_C_CONCESSIONI",rs.getString("A") );
	      params.put("ID_ORIG_C_CONC_INDIRIZZI",null );
	      params.put("DT_EXP_DATO", (Timestamp) altriParams[0]  ); 
	      params.put("DT_INI_VAL_DATO", null);
	      params.put("DT_FINE_VAL_DATO", null);
	      params.put("FLAG_DT_VAL_DATO", 0);
	      indexCount++;
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
		
	
	}








}
